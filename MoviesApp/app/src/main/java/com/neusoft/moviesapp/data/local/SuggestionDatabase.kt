package com.neusoft.moviesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.neusoft.moviesapp.data.model.Suggestion
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Suggestion::class], version = 3)
abstract class SuggestionDatabase : RoomDatabase() {
    abstract fun suggestionDao(): SuggestionDao
}