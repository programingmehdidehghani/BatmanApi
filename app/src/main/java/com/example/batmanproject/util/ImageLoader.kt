package com.example.batmanproject.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.batmanproject.R

object ImageLoader {

    fun loadImage(
        view: ImageView,
        url: String,
        placeholder: Int = R.drawable.ic_baseline_image_24
    ) {
        Glide.with(view)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .into(view)
    }
}