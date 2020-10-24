package com.jaytalekar.newsforyou

import android.net.Uri
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaytalekar.newsforyou.database.FavouriteNews
import com.jaytalekar.newsforyou.network.Article
import com.jaytalekar.newsforyou.network.Source

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

fun favouriteNewsToArticle(favNews: FavouriteNews): Article{
    val source = Source(
        id = favNews.news_Id.toString(),
        name = "database"
    )

    val article = Article(
        author = favNews.author,
        title = favNews.title,
        description = favNews.description,
        content = favNews.content,
        imageUrl = favNews.imageUrl,
        articleUrl = favNews.articleUrl,
        publishedAt = favNews.publishedAt,
        source = source
    )

    return article
}


fun articleToFavouriteNews(article: Article): FavouriteNews{
    return FavouriteNews(
        author = article.author,
        title = article.title,
        description = article.description,
        content = article.content,
        articleUrl = article.articleUrl,
        imageUrl = article.imageUrl,
        publishedAt = article.publishedAt
    )
}