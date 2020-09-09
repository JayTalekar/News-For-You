package com.jaytalekar.newsforyou.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.R
import kotlin.random.Random

class NewsAdapter : RecyclerView.Adapter<CustomViewHolder>(){

    override fun getItemCount(): Int = 30

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).
                                            inflate(R.layout.news_item, parent, false)

        return CustomViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val imageView = holder.imageView
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
    }
}

class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    val imageView : ImageView = itemView.findViewById(R.id.imageView)
}