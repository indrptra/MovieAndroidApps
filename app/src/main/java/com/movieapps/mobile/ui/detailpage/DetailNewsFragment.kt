package com.movieapps.mobile.ui.detailpage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.movieapps.mobile.R
import com.movieapps.mobile.coreandroid.base.BaseFragmentBinding
import com.movieapps.mobile.coreandroid.extensions.loadFromUrl
import com.movieapps.mobile.coreandroid.extensions.toGone
import com.movieapps.mobile.coreandroid.extensions.toVisible
import com.movieapps.mobile.databinding.FragmentDetailNewsBinding
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.ui.detailpage.rvitem.ReviewItem
import com.movieapps.mobile.ui.dto.PopularMovieListDTO
import com.movieapps.mobile.ui.homepage.MovieViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailNewsFragment :
    BaseFragmentBinding<FragmentDetailNewsBinding>(),
    ReviewItem.reviewListener {

    private val args: DetailNewsFragmentArgs by navArgs()

    private var movieDto: PopularMovieListDTO? = null
    private var isFavoriteStatus = false

    private val listReviewAdapter = GroupAdapter<GroupieViewHolder>()

    @Inject
    lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDto = args.movieDto
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailNewsBinding
        get() = FragmentDetailNewsBinding::inflate

    override fun setupView(binding: FragmentDetailNewsBinding) {
        with(binding) {
            rvListReview.adapter = listReviewAdapter
            movieDto?.let {
                var path = "https://image.tmdb.org/t/p/original${it.poster_path}"
                tvTitle.text = it.title
                tvSubTitle.text = it.title
                tvSubDesc.text = it.overview
                ivContent.loadFromUrl(path)
                ivBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
                tvDetail.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
                ivShare.setOnClickListener { share() }
                callData(it.id)
            }
            movieViewModel.uiState().observe(viewLifecycleOwner, { state ->
                when (state) {
                    is MovieViewModel.PopularMovieState.LoadingReview -> {
                        loadingIndicator.toVisible()
                    }
                    is MovieViewModel.PopularMovieState.ReviewMovieLoaded -> {
                        state.ReviewMovie.map {
                            listReviewAdapter.add(ReviewItem(it, this@DetailNewsFragment))
                        }
                        movieViewModel.getItemFavoriteMovie(movieDto?.id ?: "")
                    }
                    is MovieViewModel.PopularMovieState.ItemFavoriteMovieLoaded -> {
                        loadingIndicator.toGone()
                        isFavoriteStatus = true
                        binding.ivFavorite.setImageResource(R.drawable.ic_heart_selected)
                        ivFavorite.setOnClickListener {
                            movieDto.let {
                                movieViewModel.deleteItemFavoriteMovie(
                                    PopularMovieList(
                                        id = it?.id ?: "", poster_path = it?.poster_path ?: "", overview = it?.overview ?: "",
                                        title = it?.title ?: "", content = "", author = it?.author ?: ""
                                    )
                                )
                            }
                        }
                    }
                    is MovieViewModel.PopularMovieState.SetItemFavoriteMovieLoaded -> {
                        isFavoriteStatus = true
                        binding.ivFavorite.setImageResource(R.drawable.ic_heart_selected)
                        ivFavorite.setOnClickListener {
                            movieDto.let {
                                movieViewModel.deleteItemFavoriteMovie(
                                    PopularMovieList(
                                        id = it?.id ?: "", poster_path = it?.poster_path ?: "", overview = it?.overview ?: "",
                                        title = it?.title ?: "", content = "", author = it?.author ?: ""
                                    )
                                )
                            }
                        }
                    }
                    is MovieViewModel.PopularMovieState.deleteItemFavoriteMovieLoaded -> {
                        isFavoriteStatus = false
                        ivFavorite.setOnClickListener {
                            movieDto.let {
                                movieViewModel.setItemFavoriteMovie(
                                    PopularMovieList(
                                        id = it?.id ?: "", poster_path = it?.poster_path ?: "", overview = it?.overview ?: "",
                                        title = it?.title ?: "", content = "", author = it?.author ?: ""
                                    )
                                )
                            }
                        }
                        binding.ivFavorite.setImageResource(R.drawable.ic_heart_unselected)
                    }
                    is MovieViewModel.PopularMovieState.SetItemFavoriteError -> {
                        isFavoriteStatus = false
                        ivFavorite.setOnClickListener {
                            movieDto.let {
                                movieViewModel.setItemFavoriteMovie(
                                    PopularMovieList(
                                        id = it?.id ?: "", poster_path = it?.poster_path ?: "", overview = it?.overview ?: "",
                                        title = it?.title ?: "", content = "", author = it?.author ?: ""
                                    )
                                )
                            }
                        }
                        binding.ivFavorite.setImageResource(R.drawable.ic_heart_unselected)
                    }
                    is MovieViewModel.PopularMovieState.deleteItemFavoriteError -> {
                    }
                    is MovieViewModel.PopularMovieState.ItemFavoriteError -> {
                        loadingIndicator.toGone()
                    }
                    is MovieViewModel.PopularMovieState.ReviewError -> {
                        loadingIndicator.toGone()
                    }
                    is MovieViewModel.PopularMovieState.IsItemFavoriteMovieLoaded -> {
                        loadingIndicator.toGone()
                        if (state.isLoaded) {
                            isFavoriteStatus = true
                            ivFavorite.setOnClickListener { }
                            binding.ivFavorite.setImageResource(R.drawable.ic_heart_selected)
                        } else {
                            isFavoriteStatus = false
                            ivFavorite.setOnClickListener {
                                movieDto.let {
                                    movieViewModel.setItemFavoriteMovie(
                                        PopularMovieList(
                                            id = it?.id ?: "", poster_path = it?.poster_path ?: "", overview = it?.overview ?: "",
                                            title = it?.title ?: "", content = "", author = it?.author ?: ""
                                        )
                                    )
                                }
                            }
                            binding.ivFavorite.setImageResource(R.drawable.ic_heart_unselected)
                        }
                    }
                }
            })
        }
    }
    private fun callData(id: String) {
        movieViewModel.getReviewMovie(page = 1, id)
    }

    private fun share() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "https://www.themoviedb.org/")
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    override fun onReviewMovieSelected(item: PopularMovieList) {}

    override fun onResume() {
        super.onResume()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.hide()
    }

    override fun onStop() {
        super.onStop()
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        if (supportActionBar != null) supportActionBar.show()
    }
}
