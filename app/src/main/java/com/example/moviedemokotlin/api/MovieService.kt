package com.example.moviedemokotlin.api

import com.example.moviedemokotlin.BuildConfig
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Author: created by MarkYoung on 22/01/2019 10:24
 */
interface MovieService {

    @GET("/3/movie/popular?api_key=" + BuildConfig.API_KEY)
    fun getMovies(@Query("page") page: String): Single<MovieLoadResponse>

    companion object {
        fun getMovieService(): MovieService = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(MovieService::class.java)
    }
}