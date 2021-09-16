package com.neusoft.moviesapp.data.repository

import com.neusoft.moviesapp.data.MovieApi
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.utils.ResultHandler

class MovieRepository(private val movieApi: MovieApi) {

    suspend fun getMovies(query: String): ResultHandler<List<Movie>> {
        try {
            return ResultHandler.Success(movieApi.getMovies(query).results)
        } catch (e: Exception) {
            return ResultHandler.Error(e)
        }
    }

    suspend fun getTrendingMovies(): ResultHandler<List<Movie>> {
        try {
            return ResultHandler.Success(movieApi.getTrendingMovies().results)
        } catch (e: Exception) {
            return ResultHandler.Error(e)
        }
    }

}