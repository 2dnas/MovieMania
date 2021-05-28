package com.example.imoviesapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.imoviesapp.R
import com.example.imoviesapp.databinding.FragmentDetailsBinding
import com.example.imoviesapp.service.model.Movie

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding : FragmentDetailsBinding
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = arguments?.getParcelable<Movie>("movie")

        navController = Navigation.findNavController(view)

        Glide.with(requireContext())
            .load(movie?.movieData?.poster)
            .placeholder(R.drawable.ic_baseline_movie)
            .error(R.drawable.ic_baseline_broken_image)
            .dontTransform()
            .into(binding.detailsMovieImage)

        binding.detailsDescriptionTextView.text = movie?.description
        binding.detailsTitleTextView.text = movie?.title

        binding.detailsStartButton.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToVideoFragment()
            navController.navigate(action)
        }


    }

}