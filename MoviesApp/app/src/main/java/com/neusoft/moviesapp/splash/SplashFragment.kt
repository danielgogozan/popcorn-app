package com.neusoft.moviesapp.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.neusoft.moviesapp.R
import com.neusoft.moviesapp.databinding.FragmentSplashBinding
import java.util.*
import kotlin.concurrent.schedule

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timer("moveToSearchFragment", false).schedule(3000) {
            findNavController().navigate(R.id.action_splashFragment_to_homeScreenFragment)
        }
    }
}