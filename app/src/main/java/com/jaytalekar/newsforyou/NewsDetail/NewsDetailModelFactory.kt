package com.jaytalekar.newsforyou.NewsDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaytalekar.newsforyou.network.Article

class NewsDetailModelFactory(val article: Article) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsDetailModel::class.java)){
            return NewsDetailModel(article) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}