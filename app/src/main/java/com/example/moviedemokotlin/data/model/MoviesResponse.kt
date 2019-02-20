package com.example.moviedemokotlin.data.model

/**
 * Author: created by MarkYoung on 20/02/2019 14:48
 */
data class MoviesResponse(var page: Int, var total_pages: Int, var results: List<Movie>)