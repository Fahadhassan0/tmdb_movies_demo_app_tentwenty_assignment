package com.tmdb.movies.demo.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

//load image using glide
fun loadImage(imageView: ImageView, content: Any?) {
    Glide.with(imageView.context)
        .load(content)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}


