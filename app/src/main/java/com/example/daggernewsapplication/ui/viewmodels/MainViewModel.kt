package com.example.daggernewsapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daggernewsapplication.data.model.ArticlesItem
import com.example.daggernewsapplication.data.model.Response
import com.example.daggernewsapplication.data.repository.NewsRepository
import retrofit2.Call
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticlesItem>>()
    val articles : LiveData<List<ArticlesItem>>
                get() = _articles

    private var _queryText = "besiktas"
    val queryText: String
        get() = _queryText

    private var _page = 1
    val page: Int
        get() = _page

    private var _articlesValues = MutableLiveData<ArticlesItem>()
    val articlesValues : LiveData<ArticlesItem>
    get() = _articlesValues


  init {
      getNews(queryText, page)
  }
     fun updateText(query : String) {
        _queryText = query
    }

    fun updateArticles(articles: ArticlesItem?) {
        _articlesValues.value = articles
    }

    fun updatePage(pageNumber : Int) {
        _page = pageNumber
    }

    fun getNews(query : String, page : Int){
        val response = repository.getNews(query, page)

        response!!.enqueue(object : retrofit2.Callback<Response?> {
            override fun onResponse(call: Call<Response?>, response: retrofit2.Response<Response?>) {
                if (response.isSuccessful && response.body() != null) {
                    _articles.value = response.body()!!.articles
                }
            }
            override fun onFailure(call: Call<Response?>, t: Throwable) {
                t.message
            }
        })
    }


}