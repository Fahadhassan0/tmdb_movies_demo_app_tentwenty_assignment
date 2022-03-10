package com.tmdb.movies.demo.listeners

import com.tmdb.movies.demo.data.MovieItem

interface MoviesAdapterListener {
    fun onItemClick(item: MovieItem?)
}