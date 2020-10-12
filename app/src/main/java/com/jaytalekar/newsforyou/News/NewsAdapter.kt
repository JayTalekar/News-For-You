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
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.loadImage
import com.jaytalekar.newsforyou.network.Article


class NewsAdapter(private val onClickListener: NewsAdapter.OnClickListener) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)!!

        holder.bind(article)

        holder.newsItem.setOnClickListener {
            onClickListener.onClick(article)
        }
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val newsItem: CardView = itemView.findViewById(R.id.news_item)

        private val newsImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val newsHeader = itemView.findViewById<TextView>(R.id.news_header)

        companion object {

            fun from(parent: ViewGroup): NewsViewHolder {
                val newsView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return NewsViewHolder(newsView)
            }
        }

        fun bind(article: Article?) {
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

    }

    class OnClickListener(val clickListener : (article : Article) -> Unit){
        fun onClick(article: Article) = clickListener(article)
    }


}


