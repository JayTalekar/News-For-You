package com.jaytalekar.newsforyou.News

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaytalekar.newsforyou.ApiStatus
import com.jaytalekar.newsforyou.articleToFavouriteNews
import com.jaytalekar.newsforyou.database.NewsDatabaseDao
import com.jaytalekar.newsforyou.network.Article
import com.jaytalekar.newsforyou.network.NewsApi
import com.jaytalekar.newsforyou.network.NewsApiResponse
import kotlinx.coroutines.*

class NewsViewModel(private val country: String,
                    private val database: NewsDatabaseDao) : ViewModel(){

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _selectedNews = MutableLiveData<Article>()
    val selectedNews : LiveData<Article>
        get() = _selectedNews

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
        _status.value = ApiStatus.LOADING
        getTopHeadLines()
    }

    fun eventNavigateToNewsDetail(article: Article){
        _selectedNews.value = article
    }

    fun eventNavigateToNewsDetailCompleted(){
        _selectedNews.value = null
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
            }
            Log.i("NewsViewModel: ", "The Article List is : ${_articleList.value.toString()}")
        }

    }

    fun addFavouriteNews(article: Article){
        val favouriteNews = articleToFavouriteNews(article)

        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.insert(favouriteNews)
            }
        }
    }

    fun deleteFavouriteNews(article: Article){
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                database.delete(article.articleUrl)
            }
        }
    }
}