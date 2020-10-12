package com.jaytalekar.newsforyou.NewsDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaytalekar.newsforyou.R

class NewsDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the Fragment as usual
        val rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.fragment_news_detail, container, false)

        val article = NewsDetailFragmentArgs.fromBundle(requireArguments()).article

        val viewModelFactory = NewsDetailModelFactory(article)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailModel::class.java)

        val newsImage = rootView.findViewById<ImageView>(R.id.news_detail_image)

        val newsTitle = rootView.findViewById<TextView>(R.id.news_detail_header)

        val newsContent = rootView.findViewById<TextView>(R.id.news_detail_content)

        viewModel.selectedNews.observe(viewLifecycleOwner, Observer {
            viewModel.setImage(newsImage)
            newsTitle.text = viewModel.newsTitle
            newsContent.text = viewModel.newsContent
        })

        return rootView
    }

}