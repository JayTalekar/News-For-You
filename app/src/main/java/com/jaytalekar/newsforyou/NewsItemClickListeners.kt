package com.jaytalekar.newsforyou

import com.jaytalekar.newsforyou.network.Article

interface NewsItemClickListeners {

    fun onNewsItemClick(article: Article)

    fun onFavouriteClick(isFavourite : Boolean, article: Article)

}