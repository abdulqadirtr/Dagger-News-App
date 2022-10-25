package com.example.daggernewsapplication.di.modules

import android.app.Application
import com.example.daggernewsapplication.network.NewsApi
import com.example.daggernewsapplication.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache{
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache) : OkHttpClient{
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("apiKey", Constants.apiKey)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }

        val client = OkHttpClient.Builder().addInterceptor(requestInterceptor)
        client.cache(cache)
        return client.build()
    }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return Retrofit.Builder()
                .baseUrl(Constants.url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }

    @Provides
    @Singleton
    fun  provideApiService(retrofit : Retrofit) : NewsApi{
        return retrofit.create(NewsApi::class.java)
    }
}