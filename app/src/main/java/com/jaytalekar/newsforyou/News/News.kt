package com.jaytalekar.newsforyou.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.databinding.FragmentNewsBinding

class News : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the Fragment and get its Data Binding instance
        val binding : FragmentNewsBinding = DataBindingUtil.inflate(
                        inflater, R.layout.fragment_news, container, false)

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

        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = manager

        viewModel.navigateToNewsDetails.observe(this.viewLifecycleOwner, Observer{
            if(it == true){
                navController.navigate(R.id.action_news_to_newsDetail)
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })

        return binding.root
    }

}