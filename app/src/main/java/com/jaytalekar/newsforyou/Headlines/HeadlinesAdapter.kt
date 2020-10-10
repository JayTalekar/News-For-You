package com.jaytalekar.newsforyou.Headlines

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

class HeadlinesAdapter(private val viewModel: HeadlinesViewModel)
    : ListAdapter<Article, HeadlinesAdapter.HeadlinesViewHolder>(DiffCallback){


    companion object DiffCallback: DiffUtil.ItemCallback<Article>(){

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        return HeadlinesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val article = getItem(position)!!

        holder.bind(article)

        holder.headlinesItem.setOnClickListener {
            viewModel.eventNavigateToHeadlineDetail(article)
        }
    }

    class HeadlinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val headlinesItem: CardView = itemView.findViewById(R.id.news_item)

        private val headlinesImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val headlinesHeader = itemView.findViewById<TextView>(R.id.news_header)

        companion object{
            fun from(parent: ViewGroup): HeadlinesViewHolder{
                val headlinesView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return HeadlinesViewHolder(headlinesView)
            }
        }

        fun bind(article: Article?) {
            article?.let {

                headlinesHeader.text = it.title

                var imgUri: Uri? = null
                it.imageUrl?.let {
                    imgUri = it.toUri().buildUpon().scheme("https").build()
                }

                if (imgUri == null) {
                    headlinesImage.visibility = View.GONE
                    this.setIsRecyclable(false)
                } else {
                    Glide.with(headlinesImage.context)
                        .load(imgUri)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.loading_animation)
                                .error(R.drawable.ic_broken_image)
                        )
                        .into(headlinesImage)
                }

            }

        }

    }

}