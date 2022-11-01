package com.example.daggernewsapplication.data.model

data class Response (
    val totalResults: Int = 0,
    val articles: List<ArticlesItem>? = null,
    val status: String? = null
)