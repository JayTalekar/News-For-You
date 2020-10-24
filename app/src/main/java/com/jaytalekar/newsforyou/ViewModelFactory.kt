package com.jaytalekar.newsforyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaytalekar.newsforyou.FavouriteNews.FavouriteNewsViewModel
import com.jaytalekar.newsforyou.Headlines.HeadlinesViewModel
import com.jaytalekar.newsforyou.News.NewsViewModel
import com.jaytalekar.newsforyou.NewsSearch.NewsSearchViewModel
import com.jaytalekar.newsforyou.database.NewsDatabaseDao

class ViewModelFactory() : ViewModelProvider.Factory {

    private lateinit var database : NewsDatabaseDao

    private lateinit var country : String

    constructor(country: String) : this(){
        this.country = country
    }

    constructor(database : NewsDatabaseDao) : this(){
        this.database = database
    }

    constructor(country: String, database: NewsDatabaseDao) : this(){
        this.country = country
        this.database = database
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(country, database) as T
        }
        else if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)){
            return HeadlinesViewModel(country) as T
        }
        else if (modelClass.isAssignableFrom(NewsSearchViewModel::class.java)){
            return NewsSearchViewModel(country) as T
        }
        else if (modelClass.isAssignableFrom(FavouriteNewsViewModel::class.java)){
            return FavouriteNewsViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}