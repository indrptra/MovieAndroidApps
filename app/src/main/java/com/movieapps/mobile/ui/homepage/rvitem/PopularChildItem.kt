package com.movieapps.mobile.ui.homepage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.databinding.RvItemPopularBinding
import com.movieapps.mobile.databinding.RvItemPopularListBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class PopularChildItem(private val data: PopularMovieList, val listener: popularMovieChildListener) :
    BindableItem<RvItemPopularListBinding>() {

    interface popularMovieChildListener {
        fun onPopularMovieChildSelected(item: PopularMovieList)
    }

    override fun bind(viewBinding: RvItemPopularListBinding, position: Int) = with(viewBinding) {
        var path = "https://image.tmdb.org/t/p/original${data.poster_path}"
        imgItem.loadFromUrl(path)

        this.root.setOnClickListener { listener.onPopularMovieChildSelected(data) }
    }

    override fun getLayout(): Int = R.layout.rv_item_popular_list

    override fun initializeViewBinding(view: View): RvItemPopularListBinding = RvItemPopularListBinding.bind(view)
}