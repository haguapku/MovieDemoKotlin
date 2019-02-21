package com.example.moviedemokotlin.data

import com.example.moviedemokotlin.api.MovieService
import com.example.moviedemokotlin.data.model.MovieDetail
import com.example.moviedemokotlin.data.model.MovieDetailResponse
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 22/01/2019 15:19
 */
@Singleton
class MoviesRepository @Inject constructor(val movieService: MovieService) {

//    fun loadPopularMoviesFromServer(page: String): Single<MovieLoadResponse> = MovieService.getMovieService().getMovies(page)
    fun loadPopularMoviesFromServer(page: String): Single<MovieLoadResponse> = movieService.getPopularMovies(page)

    fun loadTopRatedMoviesFromServer(page: String): Single<MovieLoadResponse> = movieService.getTopRatedMovies(page)

    fun loadNowPlayingMoviesFromServer(page: String): Single<MovieLoadResponse> = movieService.getNowPlayingMovies(page)

    fun searchMoviesFromServer(query: String, page: String): Single<MovieLoadResponse> = movieService.searchMovies(query, page)

    fun loadMovieDetailFromServer(id: Int): Single<MovieDetail> = movieService.getMovieDetail(id)
}