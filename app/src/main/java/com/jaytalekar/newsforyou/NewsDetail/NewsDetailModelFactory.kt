package com.jaytalekar.newsforyou.NewsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsDetailModel::class.java)){
            return NewsDetailModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}