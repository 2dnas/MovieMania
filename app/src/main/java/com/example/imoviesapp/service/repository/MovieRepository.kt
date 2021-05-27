package com.example.imoviesapp.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.imoviesapp.api.ApiUtils
import com.example.imoviesapp.service.model.MovieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MovieRepository {

    fun getMovies(page : Long) : MutableLiveData<MovieModel> {
        val data = MutableLiveData<MovieModel>()


        ApiUtils.apiService?.getMovies(page)?.enqueue(object : Callback<MovieModel>{
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if(response.isSuccessful && response.body() != null){
                    data.value = response.body()
                    Log.d("error", response.body().toString())
                }else{
                    Log.d("error",response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                Log.d("error" , "erori--------------------------")
            }

        })
        return data
    }

}