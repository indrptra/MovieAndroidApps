package com.movieapps.mobile.ui.favoritepage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.databinding.RvItemFavoriteBinding
import com.movieapps.mobile.databinding.RvItemReviewBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.xwray.groupie.viewbinding.BindableItem

class FavoriteItem(private val data: PopularMovieList, val listener: favoriteListener) :
    BindableItem<RvItemFavoriteBinding>() {

    interface favoriteListener {
        fun onFavoriteMovieSelected(item: PopularMovieList)
    }

    override fun bind(viewBinding: RvItemFavoriteBinding, position: Int) = with(viewBinding) {
        var path = "https://image.tmdb.org/t/p/original${data.poster_path}"
        tvTitle.text = data.title
        tvSubTitle.text = data.overview
        imgItem.loadFromUrl(path)

        this.root.setOnClickListener { listener.onFavoriteMovieSelected(data) }
    }

    override fun getLayout(): Int = R.layout.rv_item_favorite

    override fun initializeViewBinding(view: View): RvItemFavoriteBinding = RvItemFavoriteBinding.bind(view)
}