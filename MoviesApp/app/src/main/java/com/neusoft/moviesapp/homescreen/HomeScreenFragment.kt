package com.neusoft.moviesapp.homescreen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.adapters.MovieListAdapter
import com.neusoft.moviesapp.data.model.Movie
import com.neusoft.moviesapp.databinding.FragmentHomeScreenBinding
import com.neusoft.moviesapp.detailscreen.DetailScreenFragment
import com.neusoft.moviesapp.movielist.MovieListFragment
import com.neusoft.moviesapp.utils.ResultHandler
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeScreenFragment : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModel<HomeScreenViewModel>()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTrendingMovies()
        movieListAdapter = MovieListAdapter(onMovieClick)
        binding.recyclerView.apply {
            adapter = movieListAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        viewModel.trendingMovies.observe(viewLifecycleOwner) {
            movieListAdapter.movies = it
        }

        binding.searchEt.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KEYCODE_ENTER && event?.action == ACTION_DOWN) {
                    startSearch(binding.searchEt.text.toString())
                    return true
                }
                return false
            }
        })

        binding.searchEt.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                startSearch(parent?.getItemAtPosition(position).toString())
            }

        })

        binding.viewAll.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeScreenFragment_to_movieListFragment4,
                Bundle().apply { putString(MovieListFragment.QUERY, "") })
        }

        fetchSuggestions()
    }

    private fun fetchSuggestions() = lifecycleScope.launch {
        val suggestions =
            (viewModel.fetchSuggestions() as? ResultHandler.Success)?.data?.map { it.text }
                .orEmpty()

        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            suggestions
        )
        binding.searchEt.setAdapter(arrayAdapter)
    }

    private val onMovieClick = fun(movie: Movie): Unit = findNavController()
        .navigate(R.id.action_homeScreenFragment_to_detailScreenFragment2, Bundle().apply {
            putSerializable(DetailScreenFragment.MOVIE, movie)
            putBoolean(DetailScreenFragment.POP_TO_HOME, true)
        })

    private fun startSearch(query: String?) {
        val imm =
            requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
        findNavController().navigate(
            R.id.action_homeScreenFragment_to_movieListFragment4,
            Bundle().apply { putString(MovieListFragment.QUERY, query) })
    }
}