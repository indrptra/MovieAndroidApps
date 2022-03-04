package com.movieapps.mobile.ui.homepage

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.d
import com.movieapps.mobile.coreandroid.base.BaseFragmentBinding
import com.movieapps.mobile.coreandroid.extensions.toGone
import com.movieapps.mobile.coreandroid.extensions.toVisible
import com.movieapps.mobile.databinding.FragmentListNewsBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.domain.entity.toDto
import com.movieapps.mobile.ui.favoritepage.rvitem.FavoriteItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteMovieFragment :
    BaseFragmentBinding<FragmentListNewsBinding>(),
    SharedPreferences.OnSharedPreferenceChangeListener,
    FavoriteItem.favoriteListener {

    @Inject
    lateinit var movieViewModel: MovieViewModel

    private val listAdapter = GroupAdapter<GroupieViewHolder>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListNewsBinding =
        FragmentListNewsBinding::inflate

    override fun setupView(binding: FragmentListNewsBinding) = with(binding) {
        // setupRecyclerview
        rvListNews.layoutManager = LinearLayoutManager(requireActivity())
        rvListNews.adapter = listAdapter
        setListener(binding)
        callData()
        movieViewModel.uiState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is MovieViewModel.PopularMovieState.LoadingFavorite -> {
                    loadingIndicator.toVisible()
                }

                is MovieViewModel.PopularMovieState.FavoriteMovieLoaded -> {
                    loadingIndicator.toGone()
                    swipeRefreshLayout.isRefreshing = false
                    state.FavoriteMovie.map {
                        listAdapter.add(FavoriteItem(it, this@FavoriteMovieFragment))
                    }
                    if (listAdapter.itemCount == 0) {
                        binding.emptyView.visibility = View.VISIBLE
                    } else {
                        binding.emptyView.visibility = View.GONE
                    }
                }
                is MovieViewModel.PopularMovieState.FavoriteError -> {
                    loadingIndicator.toGone()
                    swipeRefreshLayout.isRefreshing = false
                    binding.emptyView.visibility = View.VISIBLE
                    state.FavoriteMessage.map {
                        d {
                            "error : $it"
                        }
                    }
                }
            }
        })
    }

    private fun setListener(binding: FragmentListNewsBinding) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            listAdapter.clear()
            callData()
        }
    }

    private fun callData() {
        movieViewModel.getFavoriteMovie()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        lifecycleScope.launchWhenStarted {
            /**Sample data api key, change value to dinamic value*/
        }
    }

    override fun onFavoriteMovieSelected(item: PopularMovieList) {
        requireActivity().onBackPressedDispatcher.onBackPressed()
        findNavController().navigate(
            ListNewsFragmentDirections.actionListNewsFragmentToDetailNewsFragment(
                item.toDto()
            )
        )
    }

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.title = "Favorite Movie"
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.title = "Movie"
    }
}
