package com.example.moviedemokotlin.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.moviedemokotlin.viewmodel.MovieDetailViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModel
import com.example.moviedemokotlin.viewmodel.MoviesViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Author: created by MarkYoung on 20/02/2019 12:59
 */
@Module
abstract class ViewModelModule {

    @Binds @IntoMap @ViewModelKey(MoviesViewModel::class)
    internal abstract fun bindMoviesViewModel(moviesViewModel: MoviesViewModel): ViewModel

    @Binds @IntoMap @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    internal abstract fun bindMoviesViewModelFactory(moviesViewModelFactory: MoviesViewModelFactory): ViewModelProvider.Factory
}