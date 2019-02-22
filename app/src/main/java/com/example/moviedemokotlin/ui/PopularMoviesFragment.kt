package com.example.moviedemokotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviedemokotlin.R
import com.example.moviedemokotlin.api.OnItemClick
import com.example.moviedemokotlin.data.model.ApiResponse
import com.example.moviedemokotlin.data.model.Movie
import com.example.moviedemokotlin.di.Injectable
import com.example.moviedemokotlin.viewmodel.MoviesViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModelFactory
import javax.inject.Inject

/**
 * Author: created by MarkYoung on 22/01/2019 14:51
 */
class PopularMoviesFragment : Fragment(), OnItemClick, Injectable {

    @Inject lateinit var factory: MoviesViewModelFactory

    private lateinit var moviesViewModel: MoviesViewModel

    @Inject lateinit var moviesAdapter: MoviesAdapter

    private lateinit var recyclerView: RecyclerView

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    internal var lastVisibleItem: Int = 0
    private var page = 1
    private var total_pages = 0

    private var isLoading = false
    private var isPullUp = false
    private var isRefreshing = false

    private lateinit var movies: List<Movie>

    companion object {

        @JvmField
        val TAG = "mainfragment"

        fun create() = PopularMoviesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.movie_list)
        val manager = GridLayoutManager(context,2)
        recyclerView.layoutManager = manager
        recyclerView.adapter = moviesAdapter
        moviesAdapter.setOnItemClick(this)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            moviesAdapter.resetData()
            moviesViewModel.resetCachedPopularMovies()
            page = 1
            moviesViewModel.loadPopularMovies(page.toString())
        }
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                lastVisibleItem = manager.findLastVisibleItemPosition()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isRefreshing = swipeRefreshLayout.isRefreshing
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+1 == moviesAdapter.itemCount
                        && !isLoading && !isRefreshing){
                    if(!isPullUp){
                        isPullUp = true
                        if (page == total_pages){
                            moviesAdapter.changeLoadState(MoviesAdapter.NO_MORE)
                        }else {
                            moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD)
                        }
                    }else {
                        isPullUp = false
                        if (page < total_pages){
                            isLoading = true
                            page++
                            moviesViewModel.loadPopularMovies(page.toString())
                            moviesAdapter.changeLoadState(MoviesAdapter.LOADING_MORE)
                        }else
                            moviesAdapter.changeLoadState(MoviesAdapter.NO_MORE)
                    }
                }
            }
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        moviesViewModel = ViewModelProviders.of(this,factory).get(MoviesViewModel::class.java)
        moviesViewModel.getPopularMoviesLivedata().observe(viewLifecycleOwner
                , Observer<ApiResponse> { t ->
            run{
                swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
                System.out.println("--pupular----${swipeRefreshLayout.isRefreshing}")
                if(t?.moviesResponse != null) {
                    movies = t.moviesResponse.results
                    page = t.moviesResponse.page
                    total_pages = t.moviesResponse.total_pages
                    moviesAdapter.setMoviesList(t.moviesResponse.results)
//                    moviesAdapter.addMovies(t.moviesResponse.results)
                }
                else if(t?.errorMessage != null){
                    Toast.makeText(context,"fail", Toast.LENGTH_SHORT).show()
                }
                if (page == total_pages) {
                    moviesAdapter.changeLoadState(MoviesAdapter.NO_MORE)
                }else {
                    moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD)
                }
                isLoading = false
            }
        })
    }

    override fun onItemClick(view: View, position: Int) {

        val bundle = Bundle()
        bundle.putInt("position",position)
        bundle.putInt("id", movies[position].id)
        val movieDetailFragment = MovieDetailFragment.create()
        movieDetailFragment.arguments = bundle
        activity!!.supportFragmentManager!!.beginTransaction()
                .replace(R.id.container, movieDetailFragment, MovieDetailFragment.TAG)
                .addToBackStack(null)
                .commit()
    }

}