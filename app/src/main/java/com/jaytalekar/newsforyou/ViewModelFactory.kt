package com.jaytalekar.newsforyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaytalekar.newsforyou.Headlines.HeadlinesViewModel
import com.jaytalekar.newsforyou.News.NewsViewModel
import com.jaytalekar.newsforyou.NewsSearch.NewsSearchViewModel

class ViewModelFactory(private val country: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(country) as T
        }
        else if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)){
            return HeadlinesViewModel(country) as T
        }
        else if (modelClass.isAssignableFrom(NewsSearchViewModel::class.java)){
            return NewsSearchViewModel(country) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}