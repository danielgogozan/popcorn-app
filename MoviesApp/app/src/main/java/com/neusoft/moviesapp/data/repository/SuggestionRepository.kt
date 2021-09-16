package com.neusoft.moviesapp.data.repository

import android.util.Log
import com.neusoft.moviesapp.data.local.SuggestionDao
import com.neusoft.moviesapp.data.model.Suggestion
import com.neusoft.moviesapp.utils.ResultHandler

class SuggestionRepository(private val suggestionDao: SuggestionDao) {

    suspend fun save(query: String): ResultHandler<Suggestion> {
        try {
            suggestionDao.insert(Suggestion(query))
            Log.d("[SuggestionRepository]", "SUGGESTION SAVED. $query")
            return ResultHandler.Success(Suggestion(query))
        } catch (e: Exception) {
            Log.d("[SuggestionRepository]", "SUGGESTION FAILED ${e.message + e.toString()}.")
            return ResultHandler.Error(e)
        }
    }

    suspend fun fetchSuggestions(): ResultHandler<List<Suggestion>> = try {
        val suggestions = suggestionDao.getAll()
        Log.d("[SuggestionRepository]", "fetch suggestions=$suggestions")
        ResultHandler.Success(suggestions)
    } catch (e: Exception) {
        Log.d("[SuggestionRepository]", "FETCH SUGGESTION FAILED ${e.message + e.toString()}.")
        ResultHandler.Error(e)
    }
}