package com.movieapps.mobile.di.module

import android.content.Context
import com.movieapps.mobile.coreandroid.network.NetworkChecker
import com.movieapps.mobile.coreandroid.network.NetworkCheckerImpl
import com.movieapps.mobile.data.datasource.local.MovieLocalDatasource
import com.movieapps.mobile.data.datasource.local.MovieLocalDatasourceImpl
import com.movieapps.mobile.data.datasource.local.db.AppDatabase
import com.movieapps.mobile.data.datasource.remote.MovieRemoteDatasource
import com.movieapps.mobile.data.datasource.remote.MovieRemoteDatasourceImpl
import com.movieapps.mobile.data.datasource.remote.service.MovieApiServices
import com.movieapps.mobile.data.repository.MovieRepository
import com.movieapps.mobile.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideNewsRemoteDatasource(services: MovieApiServices): MovieRemoteDatasource {
        return MovieRemoteDatasourceImpl(services)
    }

    @Provides
    @ActivityScoped
    fun provideNewsLocalDatasource(appDatabase: AppDatabase): MovieLocalDatasource {
        return MovieLocalDatasourceImpl(appDatabase)
    }

    @Provides
    @ActivityScoped
    fun provideNetworkChecker(@ApplicationContext ctx: Context): NetworkChecker {
        return NetworkCheckerImpl(ctx)
    }

    @Provides
    @ActivityScoped
    fun provideNewsRepository(
        remote: MovieRemoteDatasource,
        local: MovieLocalDatasource,
        networkCheck: NetworkChecker
    ): MovieRepository {
        return MovieRepositoryImpl(remote = remote, local = local, networkChecker = networkCheck)
    }
}