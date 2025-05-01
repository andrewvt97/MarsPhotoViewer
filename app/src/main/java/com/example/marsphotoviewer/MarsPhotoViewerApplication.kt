package com.example.marsphotoviewer

import android.app.Application
import com.example.marsphotoviewer.data.AppContainer
import com.example.marsphotoviewer.data.DefaultAppContainer

class MarsPhotoViewerApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}