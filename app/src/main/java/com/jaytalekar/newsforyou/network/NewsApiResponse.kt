package com.jaytalekar.newsforyou.network

import org.json.JSONArray

data class NewsApiResponse (

    val status : String,

    val totalResults : Long,

    val articles : JSONArray
)

