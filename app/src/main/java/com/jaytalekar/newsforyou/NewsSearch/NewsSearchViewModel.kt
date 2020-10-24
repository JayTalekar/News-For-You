package com.jaytalekar.newsforyou.NewsSearch

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

class NewsSearchViewModel(private val country: String,
                        private val database: NewsDatabaseDao) : ViewModel(){

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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

    fun eventNavigateToNewsDetail(article: Article){
        _selectedNews.value = article
    }

    fun eventNavigateToNewsDetailCompleted(){
        _selectedNews.value = null
    }

    fun getSearchedNews(query: String){
        coroutineScope.launch {
            val deferred = NewsApi.retrofitService
                .getSearchedNews(query)

            try{
                //To help Display Status Image on screen
                _status.value = ApiStatus.LOADING

                _response.value = deferred.await()

                _response.value?.let{
                    _articleList.value = it.articles
                }

                _status.value = ApiStatus.DONE

            }catch (t: Throwable){
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun addFavouriteNews(article: Article){
        val favouriteNews = articleToFavouriteNews(article)

        coroutineScope.launch {
            withContext(Dispatchers.IO){
                Log.i("NewsSearch ", "Inserting Searched News")
                database.insert(favouriteNews)
                Log.i("NewsSearch ", "Done Inserting Searched News!")

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