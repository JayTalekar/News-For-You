package com.jaytalekar.newsforyou.Headlines

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
import com.jaytalekar.newsforyou.R
import kotlinx.android.synthetic.main.fragment_headlines.view.*

class Headlines : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the Fragment as usual
        rootView = LayoutInflater.from(this.activity)
            .inflate(R.layout.fragment_headlines, container, false)

        val viewModelFactory = HeadlinesViewModelFactory("in")

        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HeadlinesViewModel::class.java)

        val navController = this.findNavController()

        val adapter = HeadlinesAdapter(viewModel)

        val manager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

        val headlinesList = rootView.findViewById<RecyclerView>(R.id.headlines_list)
        headlinesList.adapter = adapter
        headlinesList.layoutManager = manager

        headlinesList.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
                val newsItemSpacing = this@Headlines.resources.getDimension(R.dimen.news_item_spacing).toInt()

                outRect.top = newsItemSpacing

                if(itemPosition % 2 == 0 ){
                    outRect.left = newsItemSpacing
                    outRect.right = newsItemSpacing / 2
                }else{
                    outRect.left = newsItemSpacing / 2
                    outRect.right = newsItemSpacing
                }
            }
        })

        viewModel.navigateToSelectedHeadline.observe(this.viewLifecycleOwner, Observer{article ->
            if(article != null){
                navController.navigate(HeadlinesDirections.actionHeadlinesFragmentToNewsDetailFragment(article))
                viewModel.eventNavigateToHeadlineDetailCompleted()
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
        val statusImageView = rootView.headlines_status_image
        val headlinesList = rootView.headlines_list
        return when(status){
            ApiStatus.LOADING -> {
                statusImageView.visibility = View.VISIBLE
                headlinesList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.loading_animation)
            }

            ApiStatus.DONE -> {
                headlinesList.visibility = View.VISIBLE
                statusImageView.visibility = View.GONE
            }

            ApiStatus.ERROR -> {
                statusImageView.visibility = View.VISIBLE
                headlinesList.visibility = View.GONE
                statusImageView.setImageResource(R.drawable.ic_connection_error)
                Toast.makeText(statusImageView.context, "No Internet Connection !", Toast.LENGTH_SHORT).show()
            }
        }
    }

}