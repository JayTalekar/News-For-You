package com.jaytalekar.newsforyou.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = "18283fc13ec9425394098ba52fcda61d"

const val HEADLINE = "top-headlines"
const val EVERYTHING = "everything"
const val SOURCES = "sources"
const val BASE_URL = "https://newsapi.org/v2/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @Headers("X-Api-Key: $API_KEY")
    @GET(HEADLINE)
    fun getHeadlines(@Query("country") country : String) : Call<String>

    @Headers("X-Api-Key: $API_KEY")
    @GET(EVERYTHING)
    fun getEveryNews(@Query("country") country : String) : Call<String>
}

object NewsApi{
    val retrofitService : NewsApiService by lazy{
        retrofit.create(NewsApiService::class.java)
    }
}