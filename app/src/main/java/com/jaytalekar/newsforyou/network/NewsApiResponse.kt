package com.jaytalekar.newsforyou.network

import com.google.gson.annotations.SerializedName

data class NewsApiResponse (

    @SerializedName("status")
    val status : String,

    @SerializedName("totalResults")
    val totalResults : Long,

    @SerializedName("articles")
    val articles : List<Article> = listOf()
)

