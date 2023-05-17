package com.example.yandexmaptest

import android.app.Application
import com.yandex.mapkit.MapKitFactory

class MapApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(BuildConfig.MAP_API_KEY)
    }
}