package com.example.imoviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.imoviesapp.api.ApiService
import com.example.imoviesapp.databinding.ActivityMainBinding
import com.example.imoviesapp.service.model.MovieModel
import com.example.imoviesapp.viewmodel.MovieViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val controller  = navHost.navController

        binding.bottomNavMenu.setupWithNavController(controller)

        controller.addOnDestinationChangedListener{ _ , destination, _ ->
            when(destination.id){
                R.id.videoFragment -> binding.bottomNavMenu.visibility = View.GONE

                else -> binding.bottomNavMenu.visibility = View.VISIBLE
            }
        }

    }
}