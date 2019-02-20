package com.example.moviedemokotlin

import android.app.Activity
import android.app.Application
import com.example.moviedemokotlin.di.AppInjector
import com.example.moviedemokotlin.di.DaggerAppComponent
import com.example.moviedemokotlin.di.Injectable
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Author: created by MarkYoung on 4/02/2019 11:49
 */
class MovieApplication: Application(), HasActivityInjector{

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
//        DaggerAppComponent.builder().application(this).build().inject(this)
        AppInjector.init(this)
    }

    companion object {
        lateinit var instance: MovieApplication
            private set
    }

    override fun activityInjector() = dispatchingAndroidInjector
}