package com.example.nearbylocations.data.network.retrofit

import retrofit2.http.GET

/**
 * A Interface for get data from network by retrofit
 */
interface RetrofitNearbyLocationInterface {

    /**
     * Call api for get nearby places around my location
     */
    @GET("")
    fun nearbyLocations()

    /**
     * Call api for show detail of a place
     */
    @GET("")
    fun locationDetail()
}