package com.jaytalekar.newsforyou.News

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaytalekar.newsforyou.ApiStatus
import com.jaytalekar.newsforyou.network.Article
import com.jaytalekar.newsforyou.network.NewsApi
import com.jaytalekar.newsforyou.network.NewsApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel(private val country: String) : ViewModel(){

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedNews = MutableLiveData<Article>()
    val navigateToSelectedNews : LiveData<Article>
        get() = _navigateToSelectedNews

    private val _response = MutableLiveData<NewsApiResponse>()
    val response : LiveData<NewsApiResponse>
        get() = _response

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList : LiveData<List<Article>>
        get() = _articleList

    private val _status = MutableLiveData<ApiStatus>()
    val status : LiveData<ApiStatus>
        get() = _status

    init {
        _articleList.value = null
        _navigateToSelectedNews.value = null
        _response.value = null
        _status.value = ApiStatus.LOADING
        getTopHeadLines()
    }

    fun eventNavigateToNewsDetail(article: Article){
        _navigateToSelectedNews.value = article
    }

    fun eventNavigateToNewsDetailCompleted(){
        _navigateToSelectedNews.value = null
    }

    fun getTopHeadLines(){
        Log.i("NewsViewModel: ", "getTopHeadLines Called")
        coroutineScope.launch {
            val deferred = NewsApi.retrofitService
                .getHeadlines(country)
            Log.i("NewsViewModel: ", "The Deferred instance is : ${deferred.toString()}")
            try{
                //To help Display Status Image on screen
                _status.value = ApiStatus.LOADING

                _response.value = deferred.await()
                if(_response.value != null){
                    _articleList.value = _response.value!!.articles
                }

                _status.value = ApiStatus.DONE
            }catch (t : Throwable){
                _status.value = ApiStatus.ERROR

                _articleList.value = listOf()
            }
            Log.i("NewsViewModel: ", "The Article List is : ${_articleList.value.toString()}")
        }

    }
}