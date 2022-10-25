package com.example.daggernewsapplication.di

import android.app.Application
import com.example.daggernewsapplication.di.modules.ApiModule
import com.example.daggernewsapplication.di.modules.AppModule
import com.example.daggernewsapplication.ui.MainActivity
import com.example.daggernewsapplication.ui.viewmodels.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

}