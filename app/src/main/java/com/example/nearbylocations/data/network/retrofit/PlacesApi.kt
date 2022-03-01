package com.example.nearbylocations.data.network.retrofit

import com.example.nearbylocations.pojo.NearbyPlaces
import com.example.nearbylocations.pojo.PlaceDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * A Interface for get data from network by retrofit
 */
interface PlacesApi {

    /**
     * Call api for get nearby places around my location
     */
    @GET("nearby")
    suspend fun nearbyLocations(
        @Query("ll") latitudeLongitude: String,
        @Query("hacc") hacc: Double? = null,
        @Query("altitude")altitude: Double?= null,
        @Query("query")query: String?= null,
        @Query("limit")limit: Int = 50
    ): NearbyPlaces

    /**
     * Call api for show detail of a place
     */
    @GET("{fsq_id}")
    suspend fun placeDetail(
        @Path("fsq_id") foursquareId: String,
        @Query("fields") fields: String? = null,
        @Query("session_token") sessionToken: String?= null
    ): PlaceDetail
}