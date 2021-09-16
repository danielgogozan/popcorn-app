package com.neusoft.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.databinding.MovieViewBinding

class MovieListAdapter(private val onMovieClick: (Movie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ROOT_IMAGE_URL = "https://image.tmdb.org/t/p/w92"
    }

    var movies = emptyList<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieViewHolder(view: MovieViewBinding) : RecyclerView.ViewHolder(view.root) {
        val moviePicture = view.moviePicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val movieViewBinding = DataBindingUtil.inflate<MovieViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie_view,
            parent,
            false
        )
        return MovieViewHolder(movieViewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder: MovieViewHolder = holder as MovieViewHolder
        val media = movies[position].poster_path
        if (media.isNullOrBlank()) {
            movieViewHolder.moviePicture.setImageResource(R.drawable.ic_launcher_background)
        } else {
            Glide.with(movieViewHolder.itemView.context)
                .load(ROOT_IMAGE_URL + media)
                .into(movieViewHolder.moviePicture)
        }
        movieViewHolder.itemView.setOnClickListener { onMovieClick.invoke(movies[position]) }
    }

    override fun getItemCount(): Int = movies.size
}