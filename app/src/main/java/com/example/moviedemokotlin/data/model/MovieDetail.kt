package com.example.moviedemokotlin.data.model

/**
 * Author: created by MarkYoung on 20/02/2019 16:53
 */
data class MovieDetail(
        val backdrop_path: String,
        val genres: List<Genre>,
        val homepage: String,
        val id: Int,
        val original_title: String,
        val overview: String,
        val popularity: Float,
        val poster_path: String,
        val production_companies: List<Company>,
        val release_date: String,
        val title: String,
        val vote_average: Float,
        val vote_count: Int)