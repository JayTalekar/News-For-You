package com.jaytalekar.newsforyou.FavouriteNews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaytalekar.newsforyou.database.FavouriteNews
import com.jaytalekar.newsforyou.database.NewsDatabaseDao
import kotlinx.coroutines.*

class FavouriteNewsViewModel(private val database: NewsDatabaseDao): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _selectedNews = MutableLiveData<FavouriteNews>()
    val selectedNews : LiveData<FavouriteNews>
        get() = _selectedNews

    private val _favNewsList = database.getAllNews()
    val favNewsList : LiveData<List<FavouriteNews>>
        get() = _favNewsList

    fun deleteFavouriteNews(favNews : FavouriteNews){
        uiScope.launch {
            delete(favNews)
        }
    }

    private suspend fun delete(favNews: FavouriteNews){
        withContext(Dispatchers.IO){
            database.delete(favNews.news_Id)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun onNavigateToNewsDetails(newsId : Long){
        uiScope.launch {
            _selectedNews.value = database.get(newsId)
        }
    }

    fun onNavigateToNewsDetailsCompleted(){
        _selectedNews.value = null
    }

}