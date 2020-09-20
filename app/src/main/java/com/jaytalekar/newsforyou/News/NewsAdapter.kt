package com.jaytalekar.newsforyou.News

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.databinding.NewsItemBroadBinding
import com.jaytalekar.newsforyou.databinding.TwoNewsItemBinding
import kotlin.random.Random

const val TWO_NEWS_ITEM = 0
const val BROAD_NEWS_ITEM = 1
const val ITEM_COUNT = 15

val RANDOM_POSITION = listOf(3,5,10,12,15)

class NewsAdapter(private val viewModel : NewsViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int = ITEM_COUNT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType){
            TWO_NEWS_ITEM -> TwoNewsViewHolder.from(parent)
            BROAD_NEWS_ITEM -> BroadNewsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is TwoNewsViewHolder->{

                holder.firstNewsImage.setImageResource(Utils.randomImage(TWO_NEWS_ITEM, 5))

                holder.secondNewsImage.setImageResource(Utils.randomImage(TWO_NEWS_ITEM, 5))

                holder.firstNewsItem.setOnClickListener {
                    viewModel.eventNavigateToNewsDetail()
                }

                holder.firstNewsHeader.setText(R.string.lorem_ipsum_header)

                holder.secondNewsHeader.setText(R.string.lorem_ipsum_header)

                holder.secondNewsItem.setOnClickListener{
                    viewModel.eventNavigateToNewsDetail()
                }

            }

            is BroadNewsViewHolder->{
                val newsImage = holder.newsImage
                val newsHeaderText = holder.newsHeaderText

                val imageResId = Utils.randomImage(BROAD_NEWS_ITEM, 5)

                newsImage.setImageResource(imageResId)

                newsHeaderText.setText(R.string.lorem_ipsum_header)

                holder.broadNewsItem.setOnClickListener{
                    viewModel.eventNavigateToNewsDetail()
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            in RANDOM_POSITION -> BROAD_NEWS_ITEM
            else -> TWO_NEWS_ITEM
        }
    }


    class BroadNewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val broadNewsItem : CardView = itemView.findViewById(R.id.broad_news_item)

        val newsImage : ImageView = itemView.findViewById(R.id.news_image_broad)
        val newsHeaderText : TextView = itemView.findViewById(R.id.news_header_text_broad)

        companion object{
            fun from(parent : ViewGroup): BroadNewsViewHolder{
                val inflater = LayoutInflater.from(parent.context)

                val binding = NewsItemBroadBinding.inflate(inflater, parent, false)

                return BroadNewsViewHolder(binding.root)
            }
        }

    }

    class TwoNewsViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val firstNewsItem : CardView = itemView.findViewById(R.id.first_news_item)
        val secondNewsItem : CardView = itemView.findViewById(R.id.second_news_item)

        val firstNewsImage : ImageView = itemView.findViewById(R.id.news_image_1)
        val firstNewsHeader : TextView = itemView.findViewById(R.id.news_header_text_1)

        val secondNewsImage : ImageView = itemView.findViewById(R.id.news_image_2)
        val secondNewsHeader : TextView = itemView.findViewById(R.id.news_header_text_2)

        companion object{
            fun from(parent : ViewGroup): TwoNewsViewHolder {
                val inflater = LayoutInflater.from(parent.context)

                val binding = TwoNewsItemBinding.inflate(inflater, parent, false)

                return TwoNewsViewHolder(binding.root)
            }
        }

    }


}

class Utils{

    companion object{

        fun randomImage(viewType : Int, until : Int) : Int{
            return when(viewType){
                TWO_NEWS_ITEM -> when(Random.nextInt(0, until)){
                    0 -> R.drawable.news_image_1
                    1 -> R.drawable.news_image_2
                    2 -> R.drawable.news_image_3
                    3 -> R.drawable.news_image_4
                    4 -> R.drawable.news_image_5
                    else -> R.drawable.news_image_1
                }

                BROAD_NEWS_ITEM -> when(Random.nextInt(0, until)){
                    0 -> R.drawable.image_broad_1
                    1 -> R.drawable.image_broad_2
                    2 -> R.drawable.image_broad_3
                    3 -> R.drawable.image_broad_4
                    4 -> R.drawable.image_broad_5
                    else -> R.drawable.image_broad_1
                }

                else -> throw IllegalArgumentException("Unknown ViewType $viewType ")
            }
        }
    }
}


