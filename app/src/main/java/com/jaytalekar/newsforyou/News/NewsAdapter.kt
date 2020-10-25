package com.jaytalekar.newsforyou.News

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.DiffCallback
import com.jaytalekar.newsforyou.NewsItemClickListeners
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.database.FavouriteNews
import com.jaytalekar.newsforyou.loadImage
import com.jaytalekar.newsforyou.network.Article


class NewsAdapter(private val newsItemClickListeners: NewsItemClickListeners) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback) {

    private lateinit var favNewsList : List<FavouriteNews>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)!!

        holder.bind(article, favNewsList)

        holder.newsItem.setOnClickListener {
            newsItemClickListeners.onNewsItemClick(article)
        }

        holder.favIcon.setOnClickListener{
            newsItemClickListeners.onFavouriteClick(
                holder.isFavourite,
                article
            )

            holder.isFavourite = holder.isFavourite.not()

            holder.setFavouriteIcon()
        }
    }

    fun submitFavouriteNewsList(favNewsList : List<FavouriteNews>){
        this.favNewsList = favNewsList
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val newsItem: CardView = itemView.findViewById(R.id.news_item)

        private val newsImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val newsHeader = itemView.findViewById<TextView>(R.id.news_header)

        val favIcon = itemView.findViewById<ImageView>(R.id.favourite_icon)

        var isFavourite = false

        companion object {

            fun from(parent: ViewGroup): NewsViewHolder {
                val newsView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return NewsViewHolder(newsView)
            }
        }

        fun bind(article: Article?, favNewsList: List<FavouriteNews>) {

            with(favIcon.context.resources){
                favIcon.layoutParams.width = getDimension(R.dimen.fav_small_icon_size).toInt()
                favIcon.layoutParams.height = getDimension(R.dimen.fav_small_icon_size).toInt()
            }

            isFavourite = checkFavouriteOrNot(article, favNewsList)

            setFavouriteIcon()

            article?.let {

                newsHeader.text = it.title

                var imgUri: Uri? = null
                it.imageUrl?.let {
                    imgUri = it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    newsImage.visibility = View.GONE
                    this.setIsRecyclable(false)
                } else {
                    imgUri?.let { uri ->
                        loadImage(uri, newsImage)
                    }
                }

            }

        }

        fun setFavouriteIcon(){
            if (isFavourite){
                favIcon.setImageResource(R.drawable.ic_heart)
            }else{
                favIcon.setImageResource(R.drawable.ic_heart_outline)
            }
        }

        private fun checkFavouriteOrNot(article: Article?, favNewsList: List<FavouriteNews>): Boolean{
            for (favNews in favNewsList){
                if (article?.articleUrl == favNews.articleUrl){
                    return true
                }
            }
            return false
        }

    }

}


