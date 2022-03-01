package com.example.nearbylocations.data.network.repository

import com.example.nearbylocations.data.network.retrofit.PlacesApi
import com.example.nearbylocations.pojo.NearbyPlaces
import com.example.nearbylocations.pojo.PlaceDetail
import com.example.nearbylocations.util.response.GeneralResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PlaceRepository {

    suspend fun nearbyPlaces(): Flow<NearbyPlaces>

    suspend fun placeDetails(): Flow<GeneralResponse<PlaceDetail>>
}

class PlaceRepositoryImpl @Inject constructor(
    private val retrofit: PlacesApi
) : PlaceRepository {
    override suspend fun nearbyPlaces() = flow {
        val response = retrofit.nearbyLocations("41.8781,-87.6298")
        if (!response.results.isNullOrEmpty())
            emit(response)
    }

    override suspend fun placeDetails(): Flow<GeneralResponse<PlaceDetail>> {
        TODO("Not yet implemented")
    }
}