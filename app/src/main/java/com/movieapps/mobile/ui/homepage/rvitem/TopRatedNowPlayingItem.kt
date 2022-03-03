package com.movieapps.mobile.ui.homepage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.databinding.RvItemPopularListBinding
import com.movieapps.mobile.databinding.RvItemTopRatedNowPlayingBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.xwray.groupie.viewbinding.BindableItem

class TopRatedNowPlayingItem(private val data: PopularMovieList, val listener: topRatedMovieChildListener) :
    BindableItem<RvItemTopRatedNowPlayingBinding>() {

    interface topRatedMovieChildListener {
        fun onTopRatedMovieChildSelected(item: PopularMovieList)
    }

    override fun bind(viewBinding: RvItemTopRatedNowPlayingBinding, position: Int) = with(viewBinding) {
        var path = "https://image.tmdb.org/t/p/original${data.poster_path}"
        imgItem.loadFromUrl(path)
        tvTitle.text = data.title
        tvSubTitle.text = data.title

        this.root.setOnClickListener { listener.onTopRatedMovieChildSelected(data) }
    }

    override fun getLayout(): Int = R.layout.rv_item_top_rated_now_playing

    override fun initializeViewBinding(view: View): RvItemTopRatedNowPlayingBinding = RvItemTopRatedNowPlayingBinding.bind(view)
}