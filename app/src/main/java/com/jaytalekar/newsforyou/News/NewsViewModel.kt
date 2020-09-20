package com.jaytalekar.newsforyou.News

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel(){

    private val _navigateToNewsDetails = MutableLiveData<Boolean>()
    val navigateToNewsDetails : LiveData<Boolean>
        get() = _navigateToNewsDetails

    init {
        _navigateToNewsDetails.value = false
    }

    fun eventNavigateToNewsDetail(){
        _navigateToNewsDetails.value = true
    }

    fun eventNavigateToNewsDetailCompleted(){
        _navigateToNewsDetails.value = false
    }
}