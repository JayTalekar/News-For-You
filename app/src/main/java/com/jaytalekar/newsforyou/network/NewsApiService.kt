package com.jaytalekar.newsforyou.network

import com.jaytalekar.newsforyou.API_KEY
import com.jaytalekar.newsforyou.EVERYTHING
import com.jaytalekar.newsforyou.HEADLINE
import com.jaytalekar.newsforyou.SOURCES
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApiService {
    @Headers("X-Api-Key: $API_KEY")
    @GET(HEADLINE)
    fun getHeadlines(@Query("country") country : String) : Deferred<NewsApiResponse>

    @Headers("X-Api-Key: $API_KEY")
    @GET(EVERYTHING)
    fun getSearchedNews(@Query("q") query : String) : Deferred<NewsApiResponse>

    @Headers("X-Api-Key: $API_KEY")
    @GET(SOURCES)
    fun getSources(@Query("country") country: String,
                       @Query("language") language : String) : Deferred<NewsApiResponse>
}

