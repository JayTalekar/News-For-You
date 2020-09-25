package com.jaytalekar.newsforyou.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.R

class News : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the Fragment as usual
        val rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.fragment_news, container, false)

        val viewModelFactory = NewsViewModelFactory()

        val viewModel = ViewModelProvider(this, viewModelFactory).
                                                                get(NewsViewModel::class.java)

        val navController = this.findNavController()

        val adapter : NewsAdapter = NewsAdapter(viewModel)
        val manager= GridLayoutManager(activity, 1)

//        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
//            override fun getSpanSize(position: Int): Int = when(position){
//                in RANDOM_POSITION -> 3
//                else -> 1
//            }
//        }

        val newsList = rootView.findViewById<RecyclerView>(R.id.news_list)
        newsList.adapter = adapter
        newsList.layoutManager = manager

        viewModel.navigateToNewsDetails.observe(this.viewLifecycleOwner, Observer{
            if(it == true){
                navController.navigate(R.id.action_news_to_newsDetail)
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })

        return rootView
    }

}