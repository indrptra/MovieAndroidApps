package com.movieapps.mobile.ui.homepage

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListMovieFragmentTest {

    @Before
    fun setUp() {

    }

    @Test
    fun cobaLaunchFragment() {
        val args = bundleOf("coba" to 1)
        val scenario = launchFragmentInContainer<ListMovieFragment>(args)

    }
}