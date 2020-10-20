package com.jaytalekar.newsforyou.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_news_table")
data class FavouriteNews(
    @PrimaryKey(autoGenerate = true)
    val news_Id : Long = 0L,

    @ColumnInfo(name = "author")
    val author : String?,

    @ColumnInfo(name = "title")
    val title : String,

    @ColumnInfo(name = "description")
    val description : String?,

    @ColumnInfo(name = "content")
    val content : String?,

    @ColumnInfo(name = "image_Url")
    val imageUrl : String?,

    @ColumnInfo(name = "article_Url")
    val articleUrl : String,

    @ColumnInfo(name = "published_At")
    val publishedAt : String
)