package com.example.moviedemokotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.moviedemokotlin.data.MoviesRepository
import com.example.moviedemokotlin.data.model.ApiResponse
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Author: created by MarkYoung on 22/01/2019 10:43
 */
class MoviesViewModel(val moviesRepository: MoviesRepository): ViewModel(){

    var popularMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var topRatedMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var nowPlayingMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var searchMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    val disposables: CompositeDisposable = CompositeDisposable()

    /*init {
        System.out.println("------hagua--------")
        loadPopularMovies("1")
        loadTopRatedMovies("1")
        loadNowPlayingMovies("1")
    }*/

    fun loadPopularMovies(page: String){

        disposables.add(moviesRepository.loadPopularMoviesFromServer(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieLoadResponse>(){
                    override fun onSuccess(t: MovieLoadResponse) {
                        popularMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        popularMoviesLiveData.value = ApiResponse(null,e.message)
                    }
                }))
    }

    fun loadTopRatedMovies(page: String){

        disposables.add(moviesRepository.loadTopRatedMoviesFromServer(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieLoadResponse>(){
                    override fun onSuccess(t: MovieLoadResponse) {
                        topRatedMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        topRatedMoviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun loadNowPlayingMovies(page: String){

        disposables.add(moviesRepository.loadNowPlayingMoviesFromServer(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieLoadResponse>(){
                    override fun onSuccess(t: MovieLoadResponse) {
                        nowPlayingMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        nowPlayingMoviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun searchMovies(query: String, page: String){

        disposables.add(moviesRepository.searchMoviesFromServer(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieLoadResponse>(){
                    override fun onSuccess(t: MovieLoadResponse) {
                        searchMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        searchMoviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun getPopularMoviesLivedata(): LiveData<ApiResponse> = popularMoviesLiveData

    fun getTopRatedMoviesLivedata(): LiveData<ApiResponse> = topRatedMoviesLiveData

    fun getNowPlayingMoviesLivedata(): LiveData<ApiResponse> = nowPlayingMoviesLiveData

    fun getSearchMoviesLivedata(): LiveData<ApiResponse> = searchMoviesLiveData

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}