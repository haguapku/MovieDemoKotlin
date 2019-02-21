package com.example.moviedemokotlin.api

import com.example.moviedemokotlin.BuildConfig
import com.example.moviedemokotlin.MovieApplication
import com.example.moviedemokotlin.data.model.MovieDetail
import com.example.moviedemokotlin.data.model.MovieDetailResponse
import com.example.moviedemokotlin.data.model.MovieLoadResponse
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Author: created by MarkYoung on 22/01/2019 10:24
 */
interface MovieService {

    @GET("/3/movie/popular?api_key=" + BuildConfig.API_KEY)
    fun getPopularMovies(@Query("page") page: String): Single<MovieLoadResponse>

    @GET("/3/movie/top_rated?api_key=" + BuildConfig.API_KEY)
    fun getTopRatedMovies(@Query("page") page: String): Single<MovieLoadResponse>

    @GET("/3/movie/now_playing?api_key=" + BuildConfig.API_KEY)
    fun getNowPlayingMovies(@Query("page") page: String): Single<MovieLoadResponse>

    @GET("/3/search/movie?api_key=" + BuildConfig.API_KEY)
    fun searchMovies(@Query("query") query: String, @Query("page") page: String): Single<MovieLoadResponse>

    @GET("/3/movie/{id}?api_key=" + BuildConfig.API_KEY)
    fun getMovieDetail(@Path("id") id: Int): Single<MovieDetail>

    companion object {
        fun create(): MovieService = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build().create(MovieService::class.java)

        private fun createOkHttpClient(): OkHttpClient {
            val cache = Cache(File(MovieApplication.instance.cacheDir, "httpCache"), (1024 * 1024 * 100).toLong())
            return OkHttpClient.Builder()
                    .cache(cache)
                    .addInterceptor(BaseIntercepter())
                    .addNetworkInterceptor(HttpCacheInterceptor())
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build()
        }
    }
}