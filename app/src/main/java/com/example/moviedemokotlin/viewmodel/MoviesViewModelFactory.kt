package com.example.moviedemokotlin.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.moviedemokotlin.api.MovieService
import com.example.moviedemokotlin.data.MoviesRepository

/**
 * Author: created by MarkYoung on 22/01/2019 11:24
 */
class MoviesViewModelFactory(val moviesRepository: MoviesRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(moviesRepository) as T
        throw IllegalArgumentException("nknown ViewModel class")
    }
}