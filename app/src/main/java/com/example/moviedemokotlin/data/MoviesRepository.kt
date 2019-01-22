package com.example.moviedemokotlin.data

import com.example.moviedemokotlin.api.MovieService
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.Single

/**
 * Author: created by MarkYoung on 22/01/2019 15:19
 */
class MoviesRepository {

    fun loadFromServer(page: String): Single<MovieLoadResponse> = MovieService.getMovieService().getMovies(page)
}