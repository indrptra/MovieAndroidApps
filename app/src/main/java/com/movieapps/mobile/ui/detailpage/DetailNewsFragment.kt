package com.movieapps.mobile.ui.detailpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.movieapps.mobile.coreandroid.base.BaseFragmentBinding
import com.movieapps.mobile.databinding.FragmentDetailNewsBinding
import com.movieapps.mobile.ui.dto.NewsDto

class DetailNewsFragment : BaseFragmentBinding<FragmentDetailNewsBinding>() {

    private val args: DetailNewsFragmentArgs by navArgs()

    private var newsDto: NewsDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsDto = args.newsDto
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailNewsBinding
        get() = FragmentDetailNewsBinding::inflate

    override fun setupView(binding: FragmentDetailNewsBinding) {
        with(binding) {
            newsDto?.let {
                newsTitle.text = it.title
            }
        }
    }
}