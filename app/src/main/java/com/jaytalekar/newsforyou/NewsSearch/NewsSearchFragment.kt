package com.jaytalekar.newsforyou.NewsSearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.ApiStatus
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_news.view.status_image
import kotlinx.android.synthetic.main.fragment_news_search.view.*

class NewsSearchFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the Fragment as usual
        rootView = LayoutInflater.from(this.context)
            .inflate(R.layout.fragment_news_search, container, false)

        val viewModelFactory = ViewModelFactory("in")

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsSearchViewModel::class.java)

        val navController = this.findNavController()

        val searchView = rootView.findViewById<SearchView>(R.id.search_view)
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                viewModel.getSearchedNews(searchView.query.toString())

                searchView.clearFocus()

                return true
            }
        })

        val adapter = NewsSearchAdapter(NewsSearchAdapter.OnClickListener {article ->
            viewModel.eventNavigateToNewsDetail(article)
        })

        val manager = GridLayoutManager(this.context, 1, GridLayoutManager.VERTICAL, false)

        val searchList = rootView.findViewById<RecyclerView>(R.id.search_list)
        searchList.adapter = adapter
        searchList.layoutManager = manager

        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer{ article ->
            if(article != null) {
                navController.navigate(NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailFragment(article))
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })

        viewModel.status.observe(this.viewLifecycleOwner, Observer {status ->
            setStatusImage(status)
        })

        viewModel.articleList.observe(this.viewLifecycleOwner, Observer {articleList ->
            adapter.submitList(articleList)
        })

        return rootView

    }

    private fun setStatusImage(status: ApiStatus){
        val statusImageView = rootView.status_image
        val searchList = rootView.search_list
        return when(status){
            ApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                searchList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }

            ApiStatus.DONE -> {
                searchList.visibility = View.VISIBLE
                statusImageView.visibility = View.GONE
            }

            ApiStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                searchList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
                Toast.makeText(statusImageView.context, "No Internet Connection !", Toast.LENGTH_SHORT).show()
            }
        }
    }
}