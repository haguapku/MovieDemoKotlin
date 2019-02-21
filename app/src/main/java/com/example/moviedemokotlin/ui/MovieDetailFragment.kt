package com.example.moviedemokotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.moviedemokotlin.R
import com.example.moviedemokotlin.data.model.MovieDetailResponse
import com.example.moviedemokotlin.databinding.FragmentMovieDetailBinding
import com.example.moviedemokotlin.di.Injectable
import com.example.moviedemokotlin.viewmodel.MovieDetailViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModelFactory
import javax.inject.Inject

/**
 * Author: created by MarkYoung on 20/02/2019 10:08
 */
class MovieDetailFragment :Fragment(), Injectable{

    @Inject lateinit var factory: MoviesViewModelFactory

    private lateinit var moviesDetailViewModel: MovieDetailViewModel

    private var movieId: Int = -1

    private lateinit var fragmentMovieDetailBinding: FragmentMovieDetailBinding

    companion object {

        @JvmField
        val TAG = "moviedetailfragment"

        @JvmField
        var preId: Int = -1

        @JvmField
        var count: Int = 0

        fun create() = MovieDetailFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = arguments!!.get("id") as Int
        System.out.println("-----$movieId")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        fragmentMovieDetailBinding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_movie_detail,container,false)
        return fragmentMovieDetailBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        moviesDetailViewModel = ViewModelProviders.of(this,factory).get(MovieDetailViewModel::class.java)
        moviesDetailViewModel.getMovieDetailLiveData().observe(this,
                Observer<MovieDetailResponse> { t ->
                    run{
                        if(t?.movieDetail != null) {
                            if (MovieDetailFragment.preId != t.movieDetail.id || MovieDetailFragment.count == 1){
                                System.out.println("-----${t.movieDetail.title}")
                                MovieDetailFragment.preId = t.movieDetail.id
                                MovieDetailFragment.count = 0
                                fragmentMovieDetailBinding.moviedetail = t.movieDetail
                            } else {
                                MovieDetailFragment.count++
                            }
                        }
                        else if(t?.errorMessage != null){
                            Toast.makeText(context,"fail", Toast.LENGTH_SHORT).show()
                        }
                        else{}
                    }
                })
        moviesDetailViewModel.loadMovieDetail(movieId)
    }
}