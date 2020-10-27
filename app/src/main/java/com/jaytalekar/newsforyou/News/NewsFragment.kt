package com.jaytalekar.newsforyou.News

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jaytalekar.newsforyou.ApiStatus
import com.jaytalekar.newsforyou.NewsItemClickListeners
import com.jaytalekar.newsforyou.R
import com.jaytalekar.newsforyou.ViewModelFactory
import com.jaytalekar.newsforyou.database.NewsDatabase
import com.jaytalekar.newsforyou.network.Article
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : Fragment() {

    private lateinit var rootView: View

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the Fragment as usual
        rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.fragment_news, container, false)

        val country = this.resources.configuration.locale.country


        //Get the instance of Database DAO for viewModel
        val database = NewsDatabase
            .getInstance(this.requireActivity().applicationContext).newsDatabaseDao

        val viewModelFactory = ViewModelFactory(country, database)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(NewsViewModel::class.java)

        val navController = this.findNavController()

        val adapter = NewsAdapter(getNewsItemClickListener())

        val manager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        val newsList = rootView.findViewById<RecyclerView>(R.id.news_list)
        newsList.adapter = adapter
        newsList.layoutManager = manager

        newsList.addItemDecoration(this.getItemDecorations())

        viewModel.selectedNews.observe(this.viewLifecycleOwner, Observer{ article ->
            if(article != null){
                navController.navigate(NewsFragmentDirections.actionNewsToNewsDetail(article))
                viewModel.eventNavigateToNewsDetailCompleted()
            }
        })

        viewModel.status.observe(this.viewLifecycleOwner, Observer {status ->
            setStatusImage(status)
        })

        viewModel.articleList.observe(this.viewLifecycleOwner, Observer {articleList ->
            adapter.submitList(articleList)
        })

        viewModel.favNewsList.observe(this.viewLifecycleOwner, Observer {favNewsList ->
            adapter.submitFavouriteNewsList(favNewsList)
        })

        return rootView
    }

    private fun setStatusImage(status: ApiStatus){
        val statusImageView = rootView.status_image
        val newsList = rootView.news_list
        return when(status){
            ApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                newsList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }

            ApiStatus.DONE -> {
                newsList.visibility = View.VISIBLE
                statusImageView.visibility = View.GONE
            }

            ApiStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                newsList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
                Toast.makeText(statusImageView.context, "No Internet Connection !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNewsItemClickListener() : NewsItemClickListeners{
        return object : NewsItemClickListeners{
            override fun onNewsItemClick(article: Article) {
                viewModel.eventNavigateToNewsDetail(article)
            }

            override fun onFavouriteClick(isFavourite: Boolean, article: Article) {
                if (isFavourite){
                    viewModel.deleteFavouriteNews(article)
                } else{
                    viewModel.addFavouriteNews(article)
                }
            }
        }
    }

    private fun getItemDecorations(): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                val newsItemSpacing = this@NewsFragment.resources.getDimension(R.dimen.news_item_spacing).toInt()

                outRect.top = newsItemSpacing

                if(itemPosition % 2 == 0 ){
                    outRect.left = newsItemSpacing
                    outRect.right = newsItemSpacing / 2
                }else{
                    outRect.left = newsItemSpacing / 2
                    outRect.right = newsItemSpacing
                }
            }
        }
    }

}