package com.neusoft.moviesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "suggestions", indices = [Index(value = ["text"], unique = true)])
data class Suggestion(

    @ColumnInfo(name = "text")
    val text: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)