package com.jaytalekar.newsforyou.News

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.network.Article

class News : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        // Inflate the Fragment as usual
//        val rootView = LayoutInflater.from(this.activity)
//            .inflate(R.layout.fragment_news, container, false)
//
//        val viewModelFactory = NewsViewModelFactory()
//
//        val viewModel = ViewModelProvider(this, viewModelFactory).
//                                                                get(NewsViewModel::class.java)
//
//        val navController = this.findNavController()
//
//        val adapter : NewsAdapter = NewsAdapter(viewModel)
//        val manager= GridLayoutManager(activity, 1)
//
////        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
////            override fun getSpanSize(position: Int): Int = when(position){
////                in RANDOM_POSITION -> 3
////                else -> 1
////            }
////        }
//
//        val newsList = rootView.findViewById<RecyclerView>(R.id.news_list)
//        newsList.adapter = adapter
//        newsList.layoutManager = manager
//
//        viewModel.navigateToNewsDetails.observe(this.viewLifecycleOwner, Observer{
//            if(it == true){
//                navController.navigate(R.id.action_news_to_newsDetail)
//                viewModel.eventNavigateToNewsDetailCompleted()
//            }
//        })
//
//        return rootView
        val rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.response_fragment, container, false)

        val viewModelFactory = NewsViewModelFactory()

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        val responseTextView : TextView = rootView.findViewById(R.id.response_text)

        viewModel.articleList.observe(this, Observer{
            it?.let{
                responseTextView.text = getFirstArticle(it)
            }
        })

        return rootView
    }

    fun getFirstArticle(articleList: List<Article>): String {
        val firstArticle = articleList.first()
        val buffer = StringBuffer()
        buffer.append(firstArticle.title + "\n")
            .append(firstArticle.author + "\n")
            .append(firstArticle.content + "\n")
            .append(firstArticle.imageUrl + "\n")
            .append(firstArticle.articleUrl)

        return buffer.toString()
    }

}