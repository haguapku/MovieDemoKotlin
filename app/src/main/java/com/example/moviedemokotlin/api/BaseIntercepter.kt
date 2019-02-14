package com.example.moviedemokotlin.api

import com.example.moviedemokotlin.util.NetWorkUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Author: created by MarkYoung on 15/02/2019 09:28
 */
class BaseIntercepter: Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        if (!NetWorkUtil.isNetWorkConnected()) {
            val maxStale = 28 * 24 * 60 * 60
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build()
        }
        val response = chain.proceed(request)
        return response
    }
}