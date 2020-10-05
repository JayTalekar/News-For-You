package com.jaytalekar.newsforyou.News

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaytalekar.newsforyou.network.Article
import com.jaytalekar.newsforyou.network.NewsApi
import com.jaytalekar.newsforyou.network.NewsApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class NewsApiStatus{ DONE, LOADING, ERROR}

class NewsViewModel(private val country: String) : ViewModel(){

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToNewsDetails = MutableLiveData<Boolean>()
    val navigateToNewsDetails : LiveData<Boolean>
        get() = _navigateToNewsDetails

    private val _response = MutableLiveData<NewsApiResponse>()
    val response : LiveData<NewsApiResponse>
        get() = _response

    private val _articleList = MutableLiveData<List<Article>>()
    val articleList : LiveData<List<Article>>
        get() = _articleList

    private val _status = MutableLiveData<NewsApiStatus>()
    val status : LiveData<NewsApiStatus>
        get() = _status

    init {
        _articleList.value = null
        _navigateToNewsDetails.value = false
        _response.value = null
        _status.value = NewsApiStatus.LOADING
        getTopHeadLines()
    }

    fun eventNavigateToNewsDetail(){
        _navigateToNewsDetails.value = true
    }

    fun eventNavigateToNewsDetailCompleted(){
        _navigateToNewsDetails.value = false
    }

    fun getTopHeadLines(){
        Log.i("NewsViewModel: ", "getTopHeadLines Called")
        coroutineScope.launch {
            val deferred = NewsApi.retrofitService
                .getHeadlines(country)
            Log.i("NewsViewModel: ", "The Deferred instance is : ${deferred.toString()}")
            try{
                //To help Display Status Image on screen
                _status.value = NewsApiStatus.LOADING

                _response.value = deferred.await()
                if(_response.value != null){
                    _articleList.value = _response.value!!.articles
                }

                _status.value = NewsApiStatus.DONE
            }catch (t : Throwable){
                _status.value = NewsApiStatus.ERROR

                _articleList.value = listOf()
            }
            Log.i("NewsViewModel: ", "The Article List is : ${_articleList.value.toString()}")
        }

    }
}