package com.jaytalekar.newsforyou.NewsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jaytalekar.newsforyou.R

class NewsDetail : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the Fragment as usual
        val rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.fragment_news_detail, container, false)

        return rootView
    }

}