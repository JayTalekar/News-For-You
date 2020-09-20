package com.jaytalekar.newsforyou.NewsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.databinding.FragmentNewsDetailBinding

class NewsDetail : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentNewsDetailBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_news_detail, container, false)

        return binding.root
    }

}