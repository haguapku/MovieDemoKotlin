package com.example.moviedemokotlin.di

import com.example.moviedemokotlin.MainActivity
import com.example.moviedemokotlin.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Author: created by MarkYoung on 4/02/2019 13:23
 */
@Module
abstract class BuildersModule{

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributePopularMoviesFragment(): PopularMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeTopRatedMoviesFragment(): TopRatedMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeNowPlayingMoviesFragment(): NowPlayingMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchMoviesFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment
}