package com.jaytalekar.newsforyou.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class NewsApiResponse (

    @SerializedName("status")
    val status : String,

    @SerializedName("totalResults")
    val totalResults : Long,

    @SerializedName("articles")
    val articles : List<Article> = listOf()
)
@Parcelize
data class Article(
    @SerializedName("source")
    val source : Source,

    @SerializedName("author")
    val author : String?, //can be null

    @SerializedName("title")
    val title : String,

    @SerializedName("description")
    val description : String?, //can be null

    @SerializedName("url")
    val articleUrl : String,

    @SerializedName("urlToImage")
    val imageUrl : String?, //can be null

    @SerializedName("publishedAt")
    val publishedAt : String,

    @SerializedName("content")
    val content : String? //can be null
): Parcelable

@Parcelize
data class Source (
    @SerializedName("id")
    val id : String?, //can be null

    @SerializedName("name")
    val name : String
): Parcelable
