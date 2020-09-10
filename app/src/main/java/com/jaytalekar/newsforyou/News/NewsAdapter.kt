package com.jaytalekar.newsforyou.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.databinding.NewsItemBinding
import kotlin.random.Random

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.CustomViewHolder>(){

    override fun getItemCount(): Int = 30

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        return CustomViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val imageView = holder.newsImage

        val likeImage = holder.likeImage

        holder.isLiked = false

        likeImage.setImageResource(R.drawable.heart_outline)

        val imageResId = when(Random.nextInt(0,10)){
            0 -> R.drawable.image_0
            1 -> R.drawable.image_1
            2 -> R.drawable.image_2
            3 -> R.drawable.image_3
            4 -> R.drawable.image_4
            5 -> R.drawable.image_5
            6 -> R.drawable.image_6
            7 -> R.drawable.image_7
            8 -> R.drawable.image_8
            9 -> R.drawable.image_9
            else -> R.drawable.image_0
        }

        imageView.setImageResource(imageResId)

        holder.itemView.setOnClickListener{
            holder.onLikeClicked()
        }
    }


    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val newsImage : ImageView = itemView.findViewById(R.id.news_image)
        val likeImage : ImageView = itemView.findViewById(R.id.like_image)
        var isLiked : Boolean = false

        companion object{
            fun from(parent : ViewGroup): CustomViewHolder{
                val inflater = LayoutInflater.from(parent.context)

                val binding = NewsItemBinding.inflate(inflater, parent, false)

                return CustomViewHolder(binding.root)
            }
        }

        fun onLikeClicked(){
            if(!isLiked) {
                likeImage.setImageResource(R.drawable.heart)
                isLiked = true
            }
            else {
                likeImage.setImageResource(R.drawable.heart_outline)
                isLiked = false
            }
        }
    }


}


