package com.jaytalekar.newsforyou.Headlines

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

class HeadlinesViewModel(private val country: String) : ViewModel(){
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToSelectedHeadline = MutableLiveData<Article>()
    val navigateToSelectedHeadline : LiveData<Article>
        get() = _navigateToSelectedHeadline

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
        _navigateToSelectedHeadline.value = null
        _response.value = null
        _status.value = ApiStatus.LOADING
        getTopHeadLines()
    }

    fun eventNavigateToHeadlineDetail(article: Article){
        _navigateToSelectedHeadline.value = article
    }

    fun eventNavigateToHeadlineDetailCompleted(){
        _navigateToSelectedHeadline.value = null
    }

    fun getTopHeadLines(){
        Log.i("HeadlinesViewModel: ", "getTopHeadLines Called")
        coroutineScope.launch {
            val deferred = NewsApi.retrofitService
                .getHeadlines(country)
            Log.i("HeadlinesViewModel: ", "The Deferred instance is : ${deferred.toString()}")
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
            Log.i("HeadlinesViewModel: ", "The Article List is : ${_articleList.value.toString()}")
        }

    }
}