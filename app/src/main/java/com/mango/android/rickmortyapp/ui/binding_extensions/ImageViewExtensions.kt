package com.mango.android.rickmortyapp.ui.binding_extensions

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("avatarImageRounded")
fun avatarImageRounded(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .circleCrop()
        .into(view)
}

@BindingAdapter("avatarImage")
fun avatarImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl)
        .into(view)
}

