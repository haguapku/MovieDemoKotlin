package com.example.moviedemokotlin.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.moviedemokotlin.api.MovieService
import com.example.moviedemokotlin.data.MoviesRepository
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 22/01/2019 11:24
 */
/*
class MoviesViewModelFactory @Inject constructor(val moviesRepository: Provider<MoviesRepository>)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java))
            return MoviesViewModel(moviesRepository.get()) as T
        throw IllegalArgumentException("nknown ViewModel class")
    }
    Map<Class<? extends ViewModel>, Provider<ViewModel>>
}*/

@Singleton
class MoviesViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>
        , @JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val creator = creators[modelClass] ?:
        creators.asIterable().firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        ?: throw IllegalArgumentException("unknown model class " + modelClass)

        return try {
            creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
