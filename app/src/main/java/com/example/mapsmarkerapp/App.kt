package com.example.mapsmarkerapp

import android.app.Application
import android.location.Geocoder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
//    @Inject
//    lateinit var activityRetainedComponentManager: ActivityRetainedComponentManager

    @Inject
    lateinit var geocoder: Geocoder // Add this line
//
//    override fun onCreate() {
//        super.onCreate()
//        SingletonComponentProvider.initialize(this)
//    }
//
//    override fun activityRetainedComponentInitializerProvider(): ActivityRetainedComponentManager {
//        return activityRetainedComponentManager
//    }
//
//    // Add this method if it doesn't already exist
//    override fun androidInjector(): AndroidInjector<Any> = SingletonComponentProvider.get()
}