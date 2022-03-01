package com.example.nearbylocations.di

import com.example.nearbylocations.data.local.repository.LocalRepository
import com.example.nearbylocations.data.local.repository.LocalRepositoryImpl
import com.example.nearbylocations.data.network.repository.PlaceRepository
import com.example.nearbylocations.data.network.repository.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A repository module for bind and create all repositories for handle api calls from them
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    /** Bind [PlaceRepository]
     * @param repository interface of [PlaceRepositoryImpl]
     * @return [PlaceRepository]
     */
    @Binds
    abstract fun bindPlaceRepository(repository: PlaceRepositoryImpl): PlaceRepository

    @Binds
    abstract fun bindLocalRepository(repository: LocalRepositoryImpl): LocalRepository
}
