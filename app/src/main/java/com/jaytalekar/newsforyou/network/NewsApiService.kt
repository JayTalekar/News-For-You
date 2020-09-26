package com.jaytalekar.newsforyou.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val API_KEY = "18283fc13ec9425394098ba52fcda61d"

const val HEADLINE = "top-headlines"
const val EVERYTHING = "everything"
const val SOURCES = "sources"
const val BASE_URL = "https://newsapi.org/v2/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @Headers("X-Api-Key: $API_KEY")
    @GET(HEADLINE)
    fun getHeadlines(@Query("country") country : String) : Deferred<NewsApiResponse>

    @Headers("X-Api-Key: $API_KEY")
    @GET(EVERYTHING)
    fun getEveryNews(@Query("country") country : String) : Deferred<NewsApiResponse>

    @Headers("X-Api-Key: $API_KEY")
    @GET(SOURCES)
    fun getFromSources(@Query("country") country: String,
                       @Query("language") language : String) : Deferred<NewsApiResponse>
}

object NewsApi{
    val retrofitService : NewsApiService by lazy{
        retrofit.create(NewsApiService::class.java)
    }
}