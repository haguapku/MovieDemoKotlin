package com.example.moviedemokotlin.data

import com.example.moviedemokotlin.api.MovieService
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 22/01/2019 15:19
 */
class MoviesRepository @Inject constructor(val movieService: MovieService) {

//    fun loadFromServer(page: String): Single<MovieLoadResponse> = MovieService.getMovieService().getMovies(page)
    fun loadFromServer(page: String): Single<MovieLoadResponse> = movieService.getMovies(page)
}