package com.tmdb.movies.demo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.databinding.ListItemMoviesBinding
import com.tmdb.movies.demo.listeners.MoviesAdapterListener
import javax.inject.Inject

class MoviesAdapter @Inject constructor() :
    PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>(VideoDiffCallback()) {

    private var listener: MoviesAdapterListener? = null

    fun setListener(moviesAdapterListener: MoviesAdapterListener?) {
        listener = moviesAdapterListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ItemViewHolder).bind(item)
    }

    inner class ItemViewHolder(
        private val binding: ListItemMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieItem?) {
            binding.setClickListener {
                binding.movieItem?.let {
                    listener?.onItemClick(it)
                }
            }

            binding.apply {
                movieItem = item
                executePendingBindings()
            }
        }
    }
}


private class VideoDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}
