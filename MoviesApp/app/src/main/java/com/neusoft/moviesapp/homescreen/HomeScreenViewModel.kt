package com.neusoft.moviesapp.homescreen

import android.util.Log
import androidx.lifecycle.*
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.data.repository.MovieRepository
import com.neusoft.moviesapp.data.repository.SuggestionRepository
import com.neusoft.moviesapp.utils.ResultHandler
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    val movieRepository: MovieRepository,
    val suggestionRepository: SuggestionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableMovies = MutableLiveData<List<Movie>>()
    private val mutableTrendingMovies = MutableLiveData<List<Movie>>()
    private val mutableOnError = MutableLiveData<Boolean>()

    val movies: LiveData<List<Movie>> = mutableMovies
    val trendingMovies: LiveData<List<Movie>> = mutableTrendingMovies
    val onError: LiveData<Boolean> = mutableOnError

    fun getQuery(): String {
        val query = savedStateHandle.getLiveData<String>("query").value
        if (query == null || query.isBlank())
            return "Trending"
        else
            return query
    }

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun getMovies() {
        val query = savedStateHandle.getLiveData<String>("query").value
        if (query.isNullOrBlank())
            getTrendingMovies()
        else
            getFilteredMovies(query)
    }

    private fun getFilteredMovies(query: String) {
        mutableOnError.value = false
        viewModelScope.launch {
            when (val result = movieRepository.getMovies(query)) {
                is ResultHandler.Success -> {
                    mutableMovies.value = result.data
                    Log.d(
                        "[HomeScreenViewModel]",
                        "list length ${result.data.size} and query ${query}"
                    )
                    if (result.data.isNotEmpty()) {
                        val suggestionResult = suggestionRepository.save(query)
                        if (suggestionResult is ResultHandler.Success)
                            Log.d("[HomeScreenViewModel]", "SUGGESTION SAVED SUCCESSFULLY")
                        else
                            Log.d(
                                "[HomeScreenViewModel]",
                                "ERROR WHILE SAVING SUGGESTION $suggestionResult"
                            )
                    } else mutableOnError.value = true

                    Log.d("[HomeScreenViewModel]", "SIZE ${result.data.size}")
                }
                is ResultHandler.Error -> {
                    mutableOnError.value = true
                    Log.d("[HomeScreenViewModel]", "ERROR ON QUERY ${result.exception}")
                }
            }
        }
    }

    fun getTrendingMovies() {
        viewModelScope.launch {
            when (val result = movieRepository.getTrendingMovies()) {
                is ResultHandler.Success -> {
                    mutableTrendingMovies.value = result.data
                    Log.d("[HomeScreenViewModel]", "TRENDING SIZE ${result.data.size}")
                }
                is ResultHandler.Error -> {
                    Log.d("[HomeScreenViewModel]", "ERROR ON QUERY ${result.exception}")
                }
            }
        }
    }


    suspend fun fetchSuggestions() = suggestionRepository.fetchSuggestions()
}