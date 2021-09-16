package com.neusoft.moviesapp.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment() {

    lateinit var binding: FragmentDetailScreenBinding

    companion object {
        val MOVIE: String = "MOVIE"
        val POP_TO_HOME: String = "POP_TO_HOME"
        const val ROOT_IMAGE_URL = "https://image.tmdb.org/t/p/w92"
    }

    private lateinit var movie: Movie
    private var popToHome: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(MOVIE))
                movie = it.getSerializable(MOVIE) as Movie
            if (it.containsKey(POP_TO_HOME))
                popToHome = it.getBoolean(POP_TO_HOME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieName.text = movie.title
        binding.year.text = movie.release_date
        binding.voteAverageTextView.text = movie.vote_average.toString()
        Glide.with(this).load(ROOT_IMAGE_URL + movie.poster_path).into(binding.moviePoster)
        binding.descriptionTextView.text = movie.overview
        binding.reviewTextView.text = movie.popularity.toString()

        binding.backImageView.setOnClickListener {
            if (popToHome)
                findNavController().navigate(R.id.action_detailScreenFragment_to_homeScreenFragment2)
            else
                findNavController().navigate(R.id.action_detailScreenFragment_to_movieListFragment4)
        }
    }


}