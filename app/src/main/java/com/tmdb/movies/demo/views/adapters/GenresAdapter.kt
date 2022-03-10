package com.tmdb.movies.demo.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tmdb.movies.demo.R
import com.tmdb.movies.demo.data.GenresItem
import com.tmdb.movies.demo.databinding.ListItemGenresBinding
import javax.inject.Inject

class GenresAdapter @Inject constructor() :
    ListAdapter<GenresItem, RecyclerView.ViewHolder>(GenresDiffCallback()) {

    val genreBackgroundColors = arrayListOf(
        R.color.genre_golden,
        R.color.genre_pink,
        R.color.genre_green,
        R.color.genre_purple
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as ItemViewHolder).bind(item, position)
    }

    inner class ItemViewHolder(
        private val binding: ListItemGenresBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GenresItem?, position: Int) {
            binding.tvGenre.text = item?.name
            binding.tvGenre.backgroundTintList = ContextCompat.getColorStateList(
                binding.tvGenre.context,
                genreBackgroundColors[position % 4]
            )
        }
    }
}


private class GenresDiffCallback : DiffUtil.ItemCallback<GenresItem>() {

    override fun areItemsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenresItem, newItem: GenresItem): Boolean {
        return oldItem == newItem
    }
}
