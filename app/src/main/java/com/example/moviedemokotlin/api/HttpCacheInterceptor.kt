package com.example.moviedemokotlin.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Author: created by MarkYoung on 15/02/2019 09:37
 */
class HttpCacheInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response = chain.proceed(request)
        val maxAge = 60
        response.newBuilder()
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "public, max-age=" + maxAge)
                .build()
        return response
    }
}