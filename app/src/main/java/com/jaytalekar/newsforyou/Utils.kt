package com.jaytalekar.newsforyou

import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaytalekar.newsforyou.network.Article

enum class ApiStatus{ DONE, LOADING, ERROR}

object DiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.source.id == newItem.source.id
    }
}

fun loadImage(imgUri: Uri, imageView: ImageView){

    Glide.with(imageView.context)
        .load(imgUri)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imageView)
}
