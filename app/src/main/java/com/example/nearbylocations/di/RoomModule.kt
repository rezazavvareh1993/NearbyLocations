package com.example.nearbylocations.di

import android.content.Context
import com.example.nearbylocations.data.local.db.PlaceDataBase
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
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context) = PlaceDataBase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideFavoriteMovieDAO(placeDataBase: PlaceDataBase) = placeDataBase.placeDAO()
}