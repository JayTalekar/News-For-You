package com.jaytalekar.newsforyou.FavouriteNews

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
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

        favNewsListView.addItemDecoration(this.getItemDecorations())

        getItemTouchHelper(adapter).attachToRecyclerView(favNewsListView)

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

    private fun getItemTouchHelper(adapter: FavouriteNewsAdapter): ItemTouchHelper {
        return ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPos = viewHolder.adapterPosition
                    val toPos = target.adapterPosition
                    return false // true if moved, false otherwise
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val favouriteNews = adapter.getFavouriteNews(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    viewModel.deleteFavouriteNews(favouriteNews)
                }
            })
    }

    private fun getItemDecorations(): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                val newsItemSpacing = this@FavouriteNewsFragment.resources.getDimension(R.dimen.news_item_spacing).toInt()

                outRect.top = newsItemSpacing

                outRect.left = newsItemSpacing
                outRect.right = newsItemSpacing
            }
        }
    }

}