package com.movieapps.mobile.ui.homepage

import android.util.Log.d
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.d
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.base.BaseFragmentBinding
import com.movieapps.mobile.coreandroid.extensions.toGone
import com.movieapps.mobile.coreandroid.extensions.toVisible
import com.movieapps.mobile.databinding.FragmentListNewsBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.domain.entity.toDto
import com.movieapps.mobile.ui.homepage.rvitem.PopularMovieItem
import com.movieapps.mobile.utility.ThreadInfoLogger
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListMovieFragment :
    BaseFragmentBinding<FragmentListNewsBinding>(),
    PopularMovieItem.popularMovieListener {

    @Inject
    lateinit var movieViewModel: MovieViewModel

    init {
        setHasOptionsMenu(true)
    }

    private val listNewsAdapter = GroupAdapter<GroupieViewHolder>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListNewsBinding =
        FragmentListNewsBinding::inflate

    override fun setupView(binding: FragmentListNewsBinding) = with(binding) {
        // setupRecyclerview
        rvListNews.layoutManager = LinearLayoutManager(requireActivity())
        rvListNews.adapter = listNewsAdapter
        setListener(binding)
        callData()
        movieViewModel.uiState().observe(viewLifecycleOwner, { state ->
            if (listNewsAdapter.groupCount < 3) {
                when (state) {
                    is MovieViewModel.PopularMovieState.Loading -> {
                        loadingIndicator.toVisible()
                    }

                    is MovieViewModel.PopularMovieState.MovieLoaded -> {
                        listNewsAdapter.add(PopularMovieItem(state.movie, 1, this@ListMovieFragment))
                        movieViewModel.getTopRatedMovie(page = 1)
                    }

                    is MovieViewModel.PopularMovieState.TopRatedMovieLoaded -> {
                        listNewsAdapter.add(PopularMovieItem(state.topMovie, 2, this@ListMovieFragment))
                        movieViewModel.getNowPlayingMovie(page = 1)
                        ThreadInfoLogger.logThreadInfo("show news viewmodel")
                    }

                    is MovieViewModel.PopularMovieState.NowPlayingMovieLoaded -> {
                        listNewsAdapter.add(PopularMovieItem(state.playingMovie, 3, this@ListMovieFragment))
                        loadingIndicator.toGone()
                        swipeRefreshLayout.isRefreshing = false

                        ThreadInfoLogger.logThreadInfo("show news viewmodel")
                    }
                    is MovieViewModel.PopularMovieState.NowPlayingError -> {
                        loadingIndicator.toGone()
                        swipeRefreshLayout.isRefreshing = false
                        state.playingMessage.map {
                            d {
                                "error : $it"
                            }
                        }
                    }

                    is MovieViewModel.PopularMovieState.TopRatedError -> {
                        loadingIndicator.toGone()
                        swipeRefreshLayout.isRefreshing = false
                        state.topRatedMessage.map {
                            d {
                                "error : $it"
                            }
                        }
                    }

                    is MovieViewModel.PopularMovieState.Error -> {
                        loadingIndicator.toGone()
                        swipeRefreshLayout.isRefreshing = false
                        state.message.map {
                            d {
                                "error : $it"
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setListener(binding: FragmentListNewsBinding) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            listNewsAdapter.clear()
            callData()
        }
    }

    private fun callData() {
        movieViewModel.getPopularMovie(page = 1)
    }

    override fun onPopularMovieSelected(item: PopularMovieList) {
        findNavController().navigate(
            ListMovieFragmentDirections.actionListNewsFragmentToDetailNewsFragment(
                item.toDto()
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.add(0, 1, 0, "Favorite")
            .setIcon(R.drawable.ic_heart_white)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 1) {
            findNavController().navigate(
                ListMovieFragmentDirections.actionListNewsFragmentToFavoriteMovieFragment()
            )
        }
        return super.onOptionsItemSelected(item)
    }
}
