package com.movieapps.mobile.ui.detailpage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.databinding.RvItemPopularListBinding
import com.movieapps.mobile.databinding.RvItemReviewBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.xwray.groupie.viewbinding.BindableItem

class ReviewItem(private val data: PopularMovieList, val listener: reviewListener) :
    BindableItem<RvItemReviewBinding>() {

    interface reviewListener {
        fun onReviewMovieSelected(item: PopularMovieList)
    }

    override fun bind(viewBinding: RvItemReviewBinding, position: Int) = with(viewBinding) {
        tvContent.text = data.content
        tvAuthorName.text = data.author

        this.root.setOnClickListener { listener.onReviewMovieSelected(data) }
    }

    override fun getLayout(): Int = R.layout.rv_item_review

    override fun initializeViewBinding(view: View): RvItemReviewBinding = RvItemReviewBinding.bind(view)
}