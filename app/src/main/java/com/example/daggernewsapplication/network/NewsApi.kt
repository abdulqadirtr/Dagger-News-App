package com.example.daggernewsapplication.network

import com.example.daggernewsapplication.data.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    fun getHeadline(@Query("q") query: String?, @Query("page") page: Int): Call<Response?>?
}