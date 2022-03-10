package com.tmdb.movies.demo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.movies.demo.data.MovieItem
import com.tmdb.movies.demo.databinding.ListItemMoviesSearchBinding
import com.tmdb.movies.demo.listeners.MoviesAdapterListener
import javax.inject.Inject

class MoviesSearchAdapter @Inject constructor() :
    PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>(MovieSearchDiffCallback()) {

    private var listener: MoviesAdapterListener? = null

    fun setListener(moviesAdapterListener: MoviesAdapterListener?) {
        listener = moviesAdapterListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemMoviesSearchBinding.inflate(
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
        private val binding: ListItemMoviesSearchBinding
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

private class MovieSearchDiffCallback : DiffUtil.ItemCallback<MovieItem>() {

    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
        return oldItem == newItem
    }
}
