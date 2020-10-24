package com.jaytalekar.newsforyou.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDatabaseDao {

    @Insert
    fun insert(favouriteNews: FavouriteNews) : Long

    @Query("SELECT * FROM favourite_news_table WHERE news_Id = :newsId")
    fun get(newsId: Long) : FavouriteNews

    @Query("SELECT * FROM favourite_news_table WHERE article_Url = :articleUrl")
    fun get(articleUrl: String) : FavouriteNews

    @Query("SELECT * FROM favourite_news_table ORDER BY news_Id DESC")
    fun getAllNews() : LiveData<List<FavouriteNews>>

    @Query("DELETE FROM favourite_news_table WHERE news_Id = :newsId")
    fun delete(newsId : Long)

    @Query("DELETE FROM favourite_news_table WHERE article_Url = :articleUrl")
    fun delete(articleUrl : String)

    @Query("DELETE FROM favourite_news_table")
    fun clear()

}