package com.jaytalekar.newsforyou.FavouriteNews

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.ViewModelFactory
import com.jaytalekar.newsforyou.database.NewsDatabase
import com.jaytalekar.newsforyou.favouriteNewsToArticle

class FavouriteNewsFragment : Fragment() {

    private lateinit var rootView : View

    private lateinit var viewModel : FavouriteNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the Fragment as usual
        rootView = LayoutInflater.from(this.context)
            .inflate(R.layout.fragment_favourite_news, container, false)

        this.setHasOptionsMenu(true)

        val database = NewsDatabase.getInstance(this.requireActivity().application).newsDatabaseDao

        val viewModelFactory = ViewModelFactory(database)

        viewModel = ViewModelProvider(this, viewModelFactory)
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

        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer{favNewsList ->
            adapter.submitList(favNewsList)
        })

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favourite_news_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_all_news) {
            viewModel.onClear()
            return true
        }
        return false
    }

}