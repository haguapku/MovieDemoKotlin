package com.example.moviedemokotlin.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.example.moviedemokotlin.MovieApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * Author: created by MarkYoung on 20/02/2019 13:14
 */
class AppInjector {

    companion object {

        fun init(movieApplication: MovieApplication) {

            DaggerAppComponent.builder().application(movieApplication).build().inject(movieApplication)
            movieApplication.registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
                override fun onActivityPaused(p0: Activity?) {

                }

                override fun onActivityResumed(p0: Activity?) {

                }

                override fun onActivityStarted(p0: Activity?) {

                }

                override fun onActivityDestroyed(p0: Activity?) {

                }

                override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

                }

                override fun onActivityStopped(p0: Activity?) {

                }

                override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
                    handleActivity(p0)
                }
            })
        }

        fun handleActivity(activity: Activity?){

            if (activity is Injectable && activity is HasSupportFragmentInjector)
                AndroidInjection.inject(activity)
            if (activity is FragmentActivity){
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object
                    : FragmentManager.FragmentLifecycleCallbacks(){
                    override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                        super.onFragmentPreAttached(fm, f, context)
                        if (f is Injectable)
                            AndroidSupportInjection.inject(f)
                    }

                },true)
            }
        }
    }
}