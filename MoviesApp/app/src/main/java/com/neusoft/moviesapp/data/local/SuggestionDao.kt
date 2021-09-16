package com.neusoft.moviesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neusoft.moviesapp.data.model.Suggestion

@Dao
interface SuggestionDao {
    @Query("SELECT * FROM suggestions ORDER BY id DESC LIMIT 10")
    suspend fun getAll(): List<Suggestion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suggestion: Suggestion)
}