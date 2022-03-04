package com.movieapps.mobile.ui.homepage

import com.movieapps.mobile.data.repository.MovieRepository
import com.movieapps.mobile.domain.usecase.GetMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class HomePageModule {

    @Provides
    fun provideMovieUseCase(repository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repository)
    }

    @Provides
    fun provideMovieViewModel(useCase: GetMovieUseCase): MovieViewModel {
        return MovieViewModel(useCase)
    }
}