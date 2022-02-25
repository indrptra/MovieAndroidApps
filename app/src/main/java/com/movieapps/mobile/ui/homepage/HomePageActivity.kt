package com.movieapps.mobile.ui.homepage

import android.view.LayoutInflater
import com.github.ajalt.timberkt.d
import com.movieapps.mobile.coreandroid.base.BaseActivityBinding
import com.movieapps.mobile.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : BaseActivityBinding<ActivityHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun setupView(binding: ActivityHomeBinding) {
        d { "setup view" }
    }

}