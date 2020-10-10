package com.jaytalekar.newsforyou.Headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HeadlinesViewModelFactory(val country: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HeadlinesViewModel::class.java)){
            return HeadlinesViewModel(country) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}