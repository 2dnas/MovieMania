package com.example.imoviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.imoviesapp.service.model.MovieModel
import com.example.imoviesapp.service.repository.MovieRepository

class MovieViewModel : ViewModel() {


    fun getMovies(page : Long) : LiveData<MovieModel> {
        return MovieRepository.getMovies(page)
    }

}