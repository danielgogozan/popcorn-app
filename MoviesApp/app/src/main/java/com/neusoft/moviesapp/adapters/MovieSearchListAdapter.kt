package com.neusoft.moviesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.databinding.MovieFromListViewBinding

class MovieSearchListAdapter(private val onMovieClick: (Movie) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ROOT_IMAGE_URL = "https://image.tmdb.org/t/p/w92"
    }

    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class MovieViewHolder(view: MovieFromListViewBinding) :
        RecyclerView.ViewHolder(view.root) {
        val title = view.titleTextView
        val year = view.yearTextView
        val review = view.reviewTextView
        val voteAverage = view.voteAverageTextView
        val poster = view.moviePicture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<MovieFromListViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie_from_list_view,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieHolder: MovieViewHolder = holder as MovieViewHolder
        val movie = movies[position]
        Glide.with(movieHolder.itemView.context).load(ROOT_IMAGE_URL + movie.poster_path)
            .into(movieHolder.poster)
        movieHolder.title.text = movie.title
        movieHolder.review.text = movie.popularity.toString()
        movieHolder.voteAverage.text = movie.vote_average.toString()
        movieHolder.year.text = movie.release_date
        movieHolder.itemView.tag = movie
        movieHolder.itemView.setOnClickListener { onMovieClick.invoke(movie) }
    }

    override fun getItemCount() = movies.size
}