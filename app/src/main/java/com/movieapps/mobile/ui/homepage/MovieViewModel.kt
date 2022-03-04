package com.movieapps.mobile.ui.homepage

import androidx.lifecycle.viewModelScope
import com.movieapps.mobile.coreandroid.BaseViewModel
import com.movieapps.mobile.coreandroid.exception.Failure
import com.movieapps.mobile.domain.entity.PopularMovieList
import com.movieapps.mobile.domain.usecase.GetMovieUseCase
import com.movieapps.mobile.utility.ThreadInfoLogger
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieUseCase: GetMovieUseCase) :
    BaseViewModel<MovieViewModel.PopularMovieState>() {

    sealed class PopularMovieState {
        object Loading : PopularMovieState()
        data class MovieLoaded(val movie: List<PopularMovieList>) : PopularMovieState()
        data class Error(val message: String) : PopularMovieState()

        object LoadingTopRated : PopularMovieState()
        data class TopRatedMovieLoaded(val topMovie: List<PopularMovieList>) : PopularMovieState()
        data class TopRatedError(val topRatedMessage: String) : PopularMovieState()

        object LoadingNowPlaying : PopularMovieState()
        data class NowPlayingMovieLoaded(val playingMovie: List<PopularMovieList>) : PopularMovieState()
        data class NowPlayingError(val playingMessage: String) : PopularMovieState()

        object LoadingReview : PopularMovieState()
        data class ReviewMovieLoaded(val ReviewMovie: List<PopularMovieList>) : PopularMovieState()
        data class ReviewError(val ReviewMessage: String) : PopularMovieState()

        object LoadingFavorite : PopularMovieState()
        data class FavoriteMovieLoaded(val FavoriteMovie: List<PopularMovieList>) : PopularMovieState()
        data class FavoriteError(val FavoriteMessage: String) : PopularMovieState()

        object LoadingItemFavorite : PopularMovieState()
        data class ItemFavoriteMovieLoaded(val ItemFavoriteMovie: List<PopularMovieList>) : PopularMovieState()
        data class IsItemFavoriteMovieLoaded(val isLoaded: Boolean) : PopularMovieState()
        data class ItemFavoriteError(val ItemFavoriteMessage: String) : PopularMovieState()

        object LoadingSetItemFavorite : PopularMovieState()
        data class SetItemFavoriteMovieLoaded(val SetItemFavoriteMovie: List<PopularMovieList>) : PopularMovieState()
        data class SetItemFavoriteError(val SetItemFavoriteMessage: String) : PopularMovieState()

        object LoadingDeleteItemFavorite : PopularMovieState()
        data class deleteItemFavoriteMovieLoaded(val deleteItemFavoriteMovie: List<PopularMovieList>) : PopularMovieState()
        data class deleteItemFavoriteError(val deleteItemFavoriteMessage: String) : PopularMovieState()
    }

    fun getPopularMovie(page: Int) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.Loading)

            ThreadInfoLogger.logThreadInfo("get top headlines viewmodel")
            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(page, 0))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.Error("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.Error("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.MovieLoaded(result))
                }
            })
        }
    }

    fun getTopRatedMovie(page: Int) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingTopRated)

            ThreadInfoLogger.logThreadInfo("get top headlines viewmodel")
            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(page, 1))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.TopRatedError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.TopRatedError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.TopRatedMovieLoaded(result))
                }
            })
        }
    }

    fun getNowPlayingMovie(page: Int) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingNowPlaying)

            ThreadInfoLogger.logThreadInfo("get top headlines viewmodel")
            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(page, 2))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.NowPlayingError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.NowPlayingError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.NowPlayingMovieLoaded(result))
                }
            })
        }
    }

    fun getReviewMovie(page: Int, id: String) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingReview)

            ThreadInfoLogger.logThreadInfo("get top headlines viewmodel")
            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(page, 3, id))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.ReviewError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.ReviewError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.ReviewMovieLoaded(result))
                }
            })
        }
    }
    fun getFavoriteMovie() {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingFavorite)

            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(1, 4))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.FavoriteError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.FavoriteError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.FavoriteMovieLoaded(result))
                }
            })
        }
    }
    fun getItemFavoriteMovie(id: String) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingItemFavorite)

            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(1, 5, id))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.ItemFavoriteError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.ItemFavoriteError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.ItemFavoriteMovieLoaded(result))
                } else {
                    uiState.postValue(PopularMovieState.IsItemFavoriteMovieLoaded(false))
                }
            })
        }
    }
    fun setItemFavoriteMovie(data: PopularMovieList) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingSetItemFavorite)

            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(1, 6, data = data))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.SetItemFavoriteError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.SetItemFavoriteError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.SetItemFavoriteMovieLoaded(result))
                }
            })
        }
    }
    fun deleteItemFavoriteMovie(data: PopularMovieList) {
        viewModelScope.launch {
            uiState.postValue(PopularMovieState.LoadingDeleteItemFavorite)

            val result =
                movieUseCase.run(GetMovieUseCase.MovieParam(1, 7, data = data))

            result.fold({ failure ->
                when (failure) {
                    is Failure.ServerError -> {
                        uiState.postValue(PopularMovieState.deleteItemFavoriteError("Server Error"))
                    }
                    else -> uiState.postValue(PopularMovieState.deleteItemFavoriteError("Unknown Error"))
                }
            }, { result ->
                if (!result.isNullOrEmpty()) {
                    uiState.postValue(PopularMovieState.deleteItemFavoriteMovieLoaded(result))
                }
            })
        }
    }
}
