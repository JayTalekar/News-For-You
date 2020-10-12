package com.jaytalekar.newsforyou.NewsSearch

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

class NewsSearchAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<Article, NewsSearchAdapter.NewsSearchViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSearchViewHolder {
        return NewsSearchViewHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NewsSearchViewHolder, position: Int) {
        val article = getItem(position)!!

        holder.bind(article)

        holder.newsSearchItem.setOnClickListener{
            onClickListener.onClick(article)
        }
    }

    class NewsSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val newsSearchItem : CardView = itemView.findViewById<CardView>(R.id.news_item)

        private val newsSearchImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val newsSearchHeader = itemView.findViewById<TextView>(R.id.news_header)

        companion object{

            fun createViewHolder(parent: ViewGroup): NewsSearchViewHolder{
                val newsSearchView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return NewsSearchViewHolder(newsSearchView)
            }
        }

        fun bind(article: Article){
            article?.let {

                newsSearchHeader.text = it.title

                var imgUri: Uri? = null
                it.imageUrl?.let {
                    imgUri = it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    newsSearchImage.visibility = View.GONE
                    this.setIsRecyclable(false)
                } else {
                    imgUri?.let { uri ->
                        loadImage(uri, newsSearchImage)
                    }
                }

            }

        }

    }

    class OnClickListener(val clickListener : (article : Article) -> Unit){
        fun onClick(article: Article) = clickListener(article)
    }
}