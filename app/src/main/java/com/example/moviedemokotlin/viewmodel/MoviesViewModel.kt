package com.example.moviedemokotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.moviedemokotlin.data.MoviesRepository
import com.example.moviedemokotlin.data.model.ApiResponse
import com.example.moviedemokotlin.data.model.Movie
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import com.example.moviedemokotlin.data.model.MoviesResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 22/01/2019 10:43
 */
@Singleton
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository): ViewModel(){

    var popularMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var topRatedMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var nowPlayingMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var searchMoviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData()

    var cachedPopularMovies: MutableList<Movie> = ArrayList()

    var cachedTopRatedMovies: MutableList<Movie> = ArrayList()

    var cachedNowPlayingMovies: MutableList<Movie> = ArrayList()

    var cachedSearchMovies: MutableList<Movie> = ArrayList()

    val disposables: CompositeDisposable = CompositeDisposable()

    init {
        loadPopularMovies("1")
        loadTopRatedMovies("1")
        loadNowPlayingMovies("1")
    }

    fun loadPopularMovies(page: String){

        disposables.add(moviesRepository.loadPopularMoviesFromServer(page)
                .map {
                    if (!cachedPopularMovies.containsAll(it.results))
                        cachedPopularMovies.addAll(it.results)
                    MoviesResponse(it.page,it.total_pages,cachedPopularMovies)
                    }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResponse>(){
                    override fun onSuccess(t: MoviesResponse) {
                        popularMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        popularMoviesLiveData.value = ApiResponse(null,e.message)
                    }
                }))
    }

    fun loadTopRatedMovies(page: String){

        disposables.add(moviesRepository.loadTopRatedMoviesFromServer(page)
                .map {
                    if (!cachedTopRatedMovies.containsAll(it.results))
                        cachedTopRatedMovies.addAll(it.results)
                    MoviesResponse(it.page,it.total_pages,cachedTopRatedMovies)
                }
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResponse>(){
                    override fun onSuccess(t: MoviesResponse) {
                        topRatedMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        topRatedMoviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun loadNowPlayingMovies(page: String){

        disposables.add(moviesRepository.loadNowPlayingMoviesFromServer(page)
                .map {
                    if (!cachedNowPlayingMovies.containsAll(it.results))
                        cachedNowPlayingMovies.addAll(it.results)
                    MoviesResponse(it.page,it.total_pages,cachedNowPlayingMovies)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResponse>(){
                    override fun onSuccess(t: MoviesResponse) {
                        nowPlayingMoviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        nowPlayingMoviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun searchMovies(query: String, page: String){

        disposables.add(moviesRepository.searchMoviesFromServer(query, page)
                .map {
                    if (!cachedSearchMovies.containsAll(it.results))
                        cachedSearchMovies.addAll(it.results)
                    MoviesResponse(it.page,it.total_pages,cachedSearchMovies)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MoviesResponse>(){
                    override fun onSuccess(t: MoviesResponse) {
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

    fun resetCachedPopularMovies() = cachedPopularMovies.clear()

    fun resetTopRatedMovies() = cachedTopRatedMovies.clear()

    fun resetNowPlayingMovies() = cachedNowPlayingMovies.clear()

    fun resetSearchMovies() = cachedSearchMovies.clear()
}