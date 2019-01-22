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

    var moviesLiveData: MutableLiveData<ApiResponse> = MutableLiveData();

    val disposables: CompositeDisposable = CompositeDisposable()

    fun loadMovies(page: String){

        disposables.add(moviesRepository.loadFromServer(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieLoadResponse>(){
                    override fun onSuccess(t: MovieLoadResponse) {
                        println("-----LoadMoviesSuccess------"+t.total_pages)
                        moviesLiveData.value = ApiResponse(t,null)
                    }
                    override fun onError(e: Throwable) {
                        println("----fail-----")
                        moviesLiveData.value = ApiResponse(null,e.message)
                    }

                }))
    }

    fun getMoviesLivedata(): LiveData<ApiResponse> = moviesLiveData

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}