package com.jaytalekar.newsforyou.News

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaytalekar.newsforyou.network.NewsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel(){

    private val _navigateToNewsDetails = MutableLiveData<Boolean>()
    val navigateToNewsDetails : LiveData<Boolean>
        get() = _navigateToNewsDetails

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    init {
        _navigateToNewsDetails.value = false
        getTopHeadLines()
    }

    fun eventNavigateToNewsDetail(){
        _navigateToNewsDetails.value = true
    }

    fun eventNavigateToNewsDetailCompleted(){
        _navigateToNewsDetails.value = false
    }

    fun getTopHeadLines(){
        NewsApi.retrofitService.getHeadlines(country = "in").enqueue(
            object : Callback<String>{
                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    _response.value = "Failure: " + t?.message
                }

                override fun onResponse(call: Call<String>?, response: Response<String>?) {
                    _response.value = response?.body()
                }
            }

        )
    }
}