package com.example.nearbylocations.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class to use Hilt
 */
@HiltAndroidApp
class NearbyPlacesApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}