package com.neusoft.moviesapp.data

import com.neusoft.moviesapp.data.model.PlainMovie
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("search/movie")
    suspend fun getMovies(@Query("query") query: String): PlainMovie

    @GET("search/trending/movie")
    suspend fun getTrendingMovies(): PlainMovie
}