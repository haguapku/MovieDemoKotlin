package com.example.moviedemokotlin.util

import android.content.Context
import android.net.ConnectivityManager
import com.example.moviedemokotlin.MovieApplication

/**
 * Author: created by MarkYoung on 15/02/2019 09:32
 */
class NetWorkUtil {
    companion object {
        fun isNetWorkConnected(): Boolean {
            val cm = MovieApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val currentNet = cm.activeNetworkInfo ?: return false
            return currentNet.isAvailable
        }
    }
}