package com.example.imoviesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imoviesapp.R
import com.example.imoviesapp.databinding.MovieListItemBinding
import com.example.imoviesapp.fragment.FeedFragment
import com.example.imoviesapp.service.model.Movie
import com.example.imoviesapp.service.model.MovieModel

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ItemViewHolder>() {
    var dataset : MovieModel? = null

    inner class ItemViewHolder (private val binding : MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: MovieModel){
            binding.root.setOnClickListener {
                listener?.invoke(dataset?.items?.get(adapterPosition))
            }
            Glide.with(itemView)
                .load(movie.items[adapterPosition].movieData?.poster)
//                .load("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_UY1200_CR90,0,630,1200_AL_.jpg")
                .placeholder(R.drawable.ic_baseline_movie)
                .error(R.drawable.ic_baseline_broken_image)
                .dontTransform()
                .into(binding.movieImageView)
            binding.movieTitleTextView.text = movie.items[adapterPosition].title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(MovieListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset
        holder.bind(item!!)
    }

    override fun getItemCount() = dataset!!.items.size

    fun setData(list: MovieModel){
        dataset = list
        notifyDataSetChanged()
    }

    fun updateData(list: MovieModel){
        dataset?.items?.addAll(list.items)
    }

    var listener : ((movie : Movie?) -> Unit)? = null

    fun setOnClickListener(listener : (movie : Movie?) -> Unit){
        this.listener = listener
    }
}

