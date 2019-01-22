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
import com.example.moviedemokotlin.data.MoviesRepository
import com.example.moviedemokotlin.data.model.ApiResponse
import com.example.moviedemokotlin.viewmodel.MoviesViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModelFactory

/**
 * Author: created by MarkYoung on 22/01/2019 14:51
 */
class MainFragment : Fragment() {

    private val factory = MoviesViewModelFactory(MoviesRepository())

    private lateinit var moviesViewModel: MoviesViewModel

    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var recyclerView: RecyclerView

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    internal var lastVisibleItem: Int = 0
    private var page = 1
    private var total_pages = 0

    private var isLoading = false
    private var isPullUp = false
    private var isRefreshing = false

    companion object {

        @JvmField
        val TAG = "mainfragment"

        fun create(): MainFragment = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        moviesAdapter = MoviesAdapter(ArrayList())
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)
        recyclerView = view.findViewById(R.id.movie_list)
        val manager = GridLayoutManager(context,2)
        recyclerView.layoutManager = manager
        recyclerView.adapter = moviesAdapter
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        swipeRefreshLayout.setOnRefreshListener {
            moviesAdapter.resetData()
            page = 1
            moviesViewModel.loadMovies(page.toString())
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
                        moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD)
                    }else {
                        isPullUp = false
                        if (page < total_pages){
                            isLoading = true
                            page++
                            moviesViewModel.loadMovies(page.toString())
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
        moviesViewModel = ViewModelProviders.of(this,factory).get(MoviesViewModel::class.java)
        moviesViewModel.getMoviesLivedata().observe(this
                , Observer<ApiResponse> { t ->
            run{
                if(t?.movieLoadResponse != null) {
                    swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
                    page = t.movieLoadResponse.page
                    total_pages = t.movieLoadResponse.total_pages
                    moviesAdapter.addMovies(t.movieLoadResponse.results)
                    moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD)
                    isLoading = false

                }
                else if(t?.errorMessage != null){
                    swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
                    moviesAdapter.changeLoadState(MoviesAdapter.PULL_UP_TO_LOAD)
                    isLoading = false
                    Toast.makeText(context,"fail", Toast.LENGTH_SHORT).show()
                }
            }
        })
        moviesViewModel.loadMovies("1")
    }
}