package com.example.daggernewsapplication.data.repository

import com.example.daggernewsapplication.network.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    fun getNews(query: String, page : Int) = newsApi.getHeadline(query, page)
}