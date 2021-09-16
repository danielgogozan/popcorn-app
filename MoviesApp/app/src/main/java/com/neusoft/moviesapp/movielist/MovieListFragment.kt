package com.neusoft.moviesapp.movielist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.adapters.MovieSearchListAdapter
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.databinding.FragmentMovieListBinding
import com.neusoft.moviesapp.detailscreen.DetailScreenFragment
import com.neusoft.moviesapp.homescreen.HomeScreenViewModel
import com.neusoft.moviesapp.utils.MyItemListDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MovieListFragment : Fragment() {

    lateinit var binding: FragmentMovieListBinding
    private lateinit var movieSearchListAdapter: MovieSearchListAdapter
    private val viewModel by viewModel<HomeScreenViewModel>()

    companion object {
        const val SPACE_HEIGHT = 15
        val QUERY: String = "QUERY"
    }

    private var query: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(QUERY)) {
                query = it.getString(QUERY).toString()
                viewModel.setQuery(query)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovies()
        movieSearchListAdapter = MovieSearchListAdapter(onMovieClick)

        viewModel.onError.observe(viewLifecycleOwner, {
            if (it) showDialog()
        })

        viewModel.movies.observe(viewLifecycleOwner, {
            movieSearchListAdapter.movies = it
        })

        viewModel.trendingMovies.observe(viewLifecycleOwner, {
            movieSearchListAdapter.movies = it
        })

        binding.movieList.apply {
            adapter = movieSearchListAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.movieList.addItemDecoration(MyItemListDecoration(SPACE_HEIGHT))

        binding.imageView6.setOnClickListener {
            findNavController().navigate(R.id.action_movieListFragment4_to_homeScreenFragment2)
        }

        binding.movieName.text = viewModel.getQuery().toUpperCase(Locale.ROOT)
    }

    private fun showDialog() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage("No result matches your search")
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton(
            getString(android.R.string.ok)
        ) { dialog, _ ->
            dialog.cancel()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private val onMovieClick = fun(movie: Movie): Unit = findNavController()
        .navigate(R.id.action_movieListFragment4_to_detailScreenFragment, Bundle().apply {
            putSerializable(DetailScreenFragment.MOVIE, movie)
        })

}