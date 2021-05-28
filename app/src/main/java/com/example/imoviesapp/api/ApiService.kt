package com.example.imoviesapp.api

import com.example.imoviesapp.service.model.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("channels/new-releases/movies")
    fun getMovies(@Query("page")page :Long) : Call<MovieModel>



}