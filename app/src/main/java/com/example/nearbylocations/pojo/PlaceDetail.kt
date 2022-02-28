package com.example.nearbylocations.pojo

data class PlaceDetail(
    val categories: List<Category>,
    val chains: List<Any>,
    val fsq_id: String,
    val geocodes: Geocodes,
    val location: Location,
    val name: String,
    val related_places: RelatedPlaces,
    val timezone: String
)