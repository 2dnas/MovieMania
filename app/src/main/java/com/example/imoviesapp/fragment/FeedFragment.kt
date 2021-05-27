package com.example.imoviesapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imoviesapp.R
import com.example.imoviesapp.adapter.MovieAdapter
import com.example.imoviesapp.databinding.FragmentFeedBinding
import com.example.imoviesapp.service.model.Movie
import com.example.imoviesapp.service.model.MovieModel
import com.example.imoviesapp.viewmodel.MovieViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter : MovieAdapter
    private lateinit var movieList : MovieModel
    private lateinit var navController: NavController
    private var PAGE = 0L;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        navController = Navigation.findNavController(view)

        adapter = MovieAdapter()
        movieList = MovieModel(arrayListOf())



        getList(PAGE)
        initRecyclerView()

    }

    fun initRecyclerView(){
        binding.moviesRecyclerview.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(isLastVisible()){
                        PAGE++
                        initList(PAGE)
                    }
                }
            })
            adapter = this@FeedFragment.adapter.apply {
                dataset = movieList

                setOnClickListener {
                    showDetailsFragment(it!!)
                }
            }
        }
    }



    private fun initList(page: Long){
        movieViewModel.getMovies(page).observe(viewLifecycleOwner,{
            adapter.updateData(it)
        })
    }

    private fun getList(page: Long){
        movieViewModel.getMovies(page).observe(viewLifecycleOwner,{
            adapter.setData(it)
        })
    }

    fun isLastVisible(): Boolean {
        val layoutManager = binding.moviesRecyclerview.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisible: Int = layoutManager.findLastVisibleItemPosition()
        val endHasReached : Boolean = lastVisible + 5 >= totalItemCount
        return totalItemCount > 0 && endHasReached
    }   


    fun showDetailsFragment(movie : Movie){
        val action = FeedFragmentDirections.actionFeedFragmentToDetailsFragment(movie)
        navController.navigate(action)
    }




}