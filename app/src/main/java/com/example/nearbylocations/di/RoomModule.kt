package com.example.nearbylocations.di

import android.content.Context
import com.example.jokerfinder.base.db.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule {
    @Provides
    fun provideDataBase(@ApplicationContext appContext: Context) = MovieDB.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideFavoriteMovieDAO(movieDB: MovieDB) = movieDB.favoriteMovieDAO()
}