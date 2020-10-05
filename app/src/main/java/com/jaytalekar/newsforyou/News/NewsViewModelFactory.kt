package com.jaytalekar.newsforyou.News

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(val country: String) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(country) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}