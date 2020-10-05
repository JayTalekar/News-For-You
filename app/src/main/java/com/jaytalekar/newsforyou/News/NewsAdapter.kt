package com.jaytalekar.newsforyou.News

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.network.Article


class NewsAdapter(private val viewModel: NewsViewModel) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position)!!)

        holder.newsItem.setOnClickListener {
            viewModel.eventNavigateToNewsDetail()
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
                    Glide.with(newsImage.context)
                        .load(imgUri)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                        )
                        .into(newsImage)
                }

            }

        }

    }


}


