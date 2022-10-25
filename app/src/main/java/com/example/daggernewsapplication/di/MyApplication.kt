package com.example.daggernewsapplication.di

import android.app.Application
import com.example.daggernewsapplication.di.modules.ApiModule
import com.example.daggernewsapplication.di.modules.AppModule

class MyApplication : Application() {

    var appComponent: AppComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().apiModule(ApiModule()).appModule(AppModule(this)).build()
    }

}