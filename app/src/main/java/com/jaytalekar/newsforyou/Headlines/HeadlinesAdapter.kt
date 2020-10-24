package com.jaytalekar.newsforyou.Headlines

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
import com.jaytalekar.newsforyou.loadImage
import com.jaytalekar.newsforyou.network.Article

class HeadlinesAdapter(private val newsItemClickListeners: NewsItemClickListeners)
    : ListAdapter<Article, HeadlinesAdapter.HeadlinesViewHolder>(DiffCallback){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        return HeadlinesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        val article = getItem(position)!!

        holder.bind(article)

        holder.headlinesItem.setOnClickListener {
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

    class HeadlinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val headlinesItem: CardView = itemView.findViewById(R.id.news_item)

        private val headlinesImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val headlinesHeader = itemView.findViewById<TextView>(R.id.news_header)

        val favIcon = itemView.findViewById<ImageView>(R.id.favourite_icon)

        var isFavourite = false

        companion object{
            fun from(parent: ViewGroup): HeadlinesViewHolder{
                val headlinesView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return HeadlinesViewHolder(headlinesView)
            }
        }

        fun bind(article: Article?) {

            with(favIcon.context.resources){
                favIcon.layoutParams.width = getDimension(R.dimen.fav_large_icon_size).toInt()
                favIcon.layoutParams.height = getDimension(R.dimen.fav_large_icon_size).toInt()
            }

            isFavourite = false

            setFavouriteIcon()

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
                    imgUri?.let { uri ->
                        loadImage(uri, headlinesImage)
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

    }

}