package com.movieapps.mobile.ui.homepage.rvitem

import android.view.View
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.databinding.RvItemNewsBinding
import com.movieapps.mobile.domain.entity.News
import com.xwray.groupie.viewbinding.BindableItem

class NewsItem(private val news: News, val listener: NewsItem.NewsListener) :
    BindableItem<RvItemNewsBinding>() {

    interface NewsListener {
        fun onNewsSelected(news: News)
    }

    override fun bind(viewBinding: RvItemNewsBinding, position: Int) = with(viewBinding) {
        newsTitle.text = news.title
        newsThumbnail.loadFromUrl(news.urlToImage)
        newsSource.text = "Source ${news.source.name}"

        this.root.setOnClickListener { listener.onNewsSelected(news) }
    }

    override fun getLayout(): Int = R.layout.rv_item_news

    override fun initializeViewBinding(view: View): RvItemNewsBinding = RvItemNewsBinding.bind(view)

}