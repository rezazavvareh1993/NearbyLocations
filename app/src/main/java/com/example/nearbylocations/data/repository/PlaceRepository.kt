package com.example.nearbylocations.data.repository

import com.example.nearbylocations.data.local.dao.PlaceDAO
import com.example.nearbylocations.data.local.db.PlaceDataBase
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.data.network.retrofit.PlacesApi
import com.example.nearbylocations.pojo.PlaceDetail
import com.example.nearbylocations.util.Resource
import com.example.nearbylocations.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlaceRepository {

    suspend fun nearbyPlaces(ll: String): Flow<Resource<List<PlaceItem>>>

    suspend fun placeDetails(fsqId: String): Flow<Resource<PlaceItem>>
}

class PlaceRepositoryImpl @Inject constructor(
    private val api: PlacesApi,
    private val dao: PlaceDAO
) : PlaceRepository {

    override suspend fun nearbyPlaces(ll: String) = networkBoundResource(
        query = {
            dao.getAllPlaces()
        },
        fetch = {
            api.nearbyLocations(latitudeLongitude = ll)
        },
        saveFetchResult = {
            if (!it.results.isNullOrEmpty()) {
                val places: List<PlaceItem> = it.results.map { resultItem ->
                    PlaceItem(
                        fsqId = resultItem.fsq_id,
                        name = resultItem.name,
                        address = resultItem.location.address,
                        icon = "${resultItem.categories.first().icon.prefix}${resultItem.categories.first().icon.suffix}",
                        distance = resultItem.distance.toString()
                    )
                }
                dao.deleteAllPlaces()
                dao.insertPlaces(places)
            }
        }
    )


    override suspend fun placeDetails(fsqId: String) = networkBoundResource(
        query = {
            dao.getPlaceInfo(fsqId)
        },
        fetch = {
            api.placeDetail(foursquareId = fsqId)
        },
        saveFetchResult = {
            dao.savePlaceInfo(it.fsq_id, it)
        }
    )
}