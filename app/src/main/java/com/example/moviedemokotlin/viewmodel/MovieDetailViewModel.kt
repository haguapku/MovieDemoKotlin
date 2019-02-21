package com.example.moviedemokotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.moviedemokotlin.data.MoviesRepository
import com.example.moviedemokotlin.data.model.ApiResponse
import com.example.moviedemokotlin.data.model.MovieDetail
import com.example.moviedemokotlin.data.model.MovieDetailResponse
import com.example.moviedemokotlin.data.model.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 20/02/2019 17:03
 */
@Singleton
class MovieDetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository): ViewModel() {

    var movieDetailLiveData: MutableLiveData<MovieDetailResponse> = MutableLiveData()

    val disposables: CompositeDisposable = CompositeDisposable()

    fun loadMovieDetail(id: Int){

        disposables.add(moviesRepository.loadMovieDetailFromServer(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetail>(){
                    override fun onSuccess(t: MovieDetail) {
                        movieDetailLiveData.value = MovieDetailResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        movieDetailLiveData.value = MovieDetailResponse(null,e.message)
                    }
                }))
    }

    fun getMovieDetailLiveData(): LiveData<MovieDetailResponse> = movieDetailLiveData

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}