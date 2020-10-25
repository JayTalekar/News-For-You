package com.jaytalekar.newsforyou.FavouriteNews

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
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.database.FavouriteNews
import com.jaytalekar.newsforyou.loadImage

class FavouriteNewsAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<FavouriteNews, FavouriteNewsAdapter.FavouriteNewsHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<FavouriteNews>(){

        override fun areItemsTheSame(oldItem: FavouriteNews, newItem: FavouriteNews): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: FavouriteNews, newItem: FavouriteNews): Boolean {
            return oldItem.news_Id == newItem.news_Id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteNewsHolder {
        return FavouriteNewsHolder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: FavouriteNewsHolder, position: Int) {

        val favNews = getItem(position)

        holder.bind(favNews)

        holder.favNewsItem.setOnClickListener{
            onClickListener.onClick(favNews)
        }

    }

    fun getFavouriteNews(position: Int): FavouriteNews {
        return getItem(position)
    }

    class FavouriteNewsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val favNewsItem : CardView = itemView.findViewById<CardView>(R.id.news_item)

        private val favNewsImage = itemView.findViewById<ImageView>(R.id.news_image)

        private val favNewsHeader = itemView.findViewById<TextView>(R.id.news_header)

        companion object{
            fun createViewHolder(parent : ViewGroup): FavouriteNewsHolder{
                val favNewsview = LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item, parent, false)

                return FavouriteNewsHolder(favNewsview)
            }
        }

        fun bind(favouriteNews : FavouriteNews){
            favouriteNews?.let {

                favNewsHeader.text = it.title

                var imgUri : Uri? = null
                it.imageUrl?.let {
                    imgUri = it.toUri().buildUpon().scheme("https").build()
                }

                if(imgUri == null){
                    favNewsImage.visibility = View.GONE
                    this.setIsRecyclable(false)
                }else {
                    imgUri?.let { uri ->
                        loadImage(uri, favNewsImage)
                    }
                }

            }
        }

    }


    class OnClickListener(val clickListener : (favNews : FavouriteNews) -> Unit){
        fun onClick(favNews : FavouriteNews) = clickListener(favNews)
    }
}