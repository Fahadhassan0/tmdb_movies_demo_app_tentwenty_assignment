package com.tmdb.movies.demo.views.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.tmdb.movies.demo.utilities.IMAGE_BASE_URL
import com.tmdb.movies.demo.utilities.loadImage

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        loadImage(view, IMAGE_BASE_URL + imageUrl)
    }
}

