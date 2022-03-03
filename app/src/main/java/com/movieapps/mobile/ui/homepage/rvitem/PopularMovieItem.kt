package com.movieapps.mobile.ui.homepage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.databinding.RvItemPopularBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class PopularMovieItem(private val data: List<PopularMovieList>, private val status: Int, val listener: popularMovieListener) :
    BindableItem<RvItemPopularBinding>(), PopularChildItem.popularMovieChildListener,
    TopRatedNowPlayingItem.topRatedMovieChildListener {

    private val listAdapter = GroupAdapter<GroupieViewHolder>()

    interface popularMovieListener {
        fun onPopularMovieSelected(item: PopularMovieList)
    }

    override fun bind(viewBinding: RvItemPopularBinding, position: Int) = with(viewBinding) {
        tvTitle.text = when (status) {
            1 -> "Popular"
            2 -> "Top Rated"
            else -> "Now playing"
        }
        rvListPopular.adapter = listAdapter
        listAdapter.clear()
        when (status) {
            1 -> data.map {
                listAdapter.add(PopularChildItem(it, this@PopularMovieItem))
            }
            2 -> data.map {
                listAdapter.add(TopRatedNowPlayingItem(it, this@PopularMovieItem))
            }
            else -> data.map {
                listAdapter.add(TopRatedNowPlayingItem(it, this@PopularMovieItem))
            }
        }
        this.root.setOnClickListener {}
    }

    override fun getLayout(): Int = R.layout.rv_item_popular

    override fun initializeViewBinding(view: View): RvItemPopularBinding = RvItemPopularBinding.bind(view)
    override fun onPopularMovieChildSelected(item: PopularMovieList) {
        listener.onPopularMovieSelected(item)
    }

    override fun onTopRatedMovieChildSelected(item: PopularMovieList) {
        listener.onPopularMovieSelected(item)
    }
}