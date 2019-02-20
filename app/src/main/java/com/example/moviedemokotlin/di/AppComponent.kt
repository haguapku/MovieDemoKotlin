package com.example.moviedemokotlin.di

import android.app.Application
import com.example.moviedemokotlin.MovieApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Author: created by MarkYoung on 4/02/2019 11:18
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    BuildersModule::class,
    ViewModelModule::class])
interface AppComponent{

    @Component.Builder
    interface Bulider{
        @BindsInstance
        fun application(application: Application): Bulider
        fun build(): AppComponent
    }

    fun inject(movieApplication: MovieApplication)
}