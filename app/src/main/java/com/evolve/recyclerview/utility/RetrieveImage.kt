package com.evolve.recyclerview.utility

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun retrieveImage (
    placeholder: Int,
    view: View,
    url: String,
    target: ImageView
){
    val requestOptions = RequestOptions()
        .placeholder(placeholder)
        .error(placeholder)
    Glide.with(view)
        .applyDefaultRequestOptions(requestOptions)
        .load(url)
        .into(target)
}