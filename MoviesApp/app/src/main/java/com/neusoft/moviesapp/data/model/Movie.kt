package com.neusoft.moviesapp.data.model

import java.io.Serializable

data class Movie(
    val id: Double?,
    val title: String?,
    val overview: String?,
    val popularity: Float?,
    val release_date: String?,
    val vote_average: Float?,
    val poster_path: String?,
) : Serializable
{
    override fun toString(): String = id.toString() + title + overview + release_date + vote_average + poster_path
}