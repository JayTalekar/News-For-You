package com.jaytalekar.newsforyou.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        val adapter : NewsAdapter = NewsAdapter()
        val manager= GridLayoutManager(activity, 3)

        binding.newsList.adapter = adapter
        binding.newsList.layoutManager = manager

        return binding.root
    }

}