package com.jaytalekar.newsforyou.FavouriteNews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jaytalekar.newsforyou.database.NewsDatabase
import com.jaytalekar.newsforyou.favouriteNewsToArticle
import kotlinx.android.synthetic.main.fragment_favourite_news.view.*

class FavouriteNewsFragment : Fragment() {

    private lateinit var rootView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the Fragment as usual
        rootView = LayoutInflater.from(this.context)
            .inflate(R.layout.fragment_favourite_news, container, false)

        val database = NewsDatabase.getInstance(this.requireActivity().application).newsDatabaseDao

        val viewModelFactory = ViewModelFactory(database)

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouriteNewsViewModel::class.java)

        val navController = this.findNavController()

        val adapter = FavouriteNewsAdapter(FavouriteNewsAdapter.OnClickListener {favNews ->
            viewModel.onNavigateToNewsDetails(favNews.news_Id)
        })

        val manager = GridLayoutManager(this.context, 1, GridLayoutManager.VERTICAL, false)

        val favNewsListView = rootView.findViewById<RecyclerView>(R.id.favourite_news_list)
        favNewsListView.adapter = adapter
        favNewsListView.layoutManager = manager

        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer {favNews ->
            if (favNews != null) {
                val article = favouriteNewsToArticle(favNews)
                navController.navigate(FavouriteNewsFragmentDirections.actionFavouriteNewsFragmentToNewsDetailFragment(article))
                viewModel.onNavigateToNewsDetailsCompleted()
            }
        })

        viewModel.status.observe(this.viewLifecycleOwner, Observer {status ->
            setStatusImage(status)
        })

        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer{favNewsList ->
            adapter.submitList(favNewsList)
        })

        return rootView
    }

    private fun setStatusImage(status: ApiStatus){
        val statusImageView = rootView.favourite_status_image
        val favNewsList = rootView.favourite_news_list
        return when(status){
            ApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                favNewsList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }

            ApiStatus.DONE -> {
                favNewsList.visibility = View.VISIBLE
                statusImageView.visibility = View.GONE
            }

            ApiStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                favNewsList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
                Toast.makeText(statusImageView.context, "No Internet Connection !", Toast.LENGTH_SHORT).show()
            }
        }
    }
}