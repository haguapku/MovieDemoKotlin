package com.example.moviedemokotlin.di

import com.example.moviedemokotlin.api.MovieService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 4/02/2019 10:52
 */
@Module class AppModule{

    @Provides @Singleton fun getMovieService(): MovieService = MovieService.create()
}