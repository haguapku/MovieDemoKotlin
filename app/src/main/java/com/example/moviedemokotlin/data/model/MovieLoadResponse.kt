package com.example.moviedemokotlin.data.model

/**
 * Author: created by MarkYoung on 22/01/2019 10:19
 */
data class MovieLoadResponse(val page: Int, val total_pages: Int, val results: List<Movie>)