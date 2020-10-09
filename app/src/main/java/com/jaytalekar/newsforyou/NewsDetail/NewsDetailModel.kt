package com.jaytalekar.newsforyou.NewsDetail

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.network.Article

class NewsDetailModel(article: Article) : ViewModel(){

    private val _selectedNews = MutableLiveData<Article>()
    val selectedNews : LiveData<Article>
        get() = _selectedNews

    init {
        _selectedNews.value = article
    }

    private val imageUrl = _selectedNews.value?.imageUrl

    val newsTitle = _selectedNews.value?.title

    val newsContent = _selectedNews.value?.content

    fun setImage(newsImageView : ImageView){

        var imgUri: Uri? = null

        imageUrl?.let {
            imgUri = it.toUri().buildUpon().scheme("https").build()
        }

        if (imgUri == null) {
            newsImageView.visibility = View.GONE
        } else {
            Glide.with(newsImageView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(newsImageView)
        }
    }

}