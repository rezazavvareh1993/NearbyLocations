package com.example.nearbylocations.pojo

data class Location(
    val address: String,
    val address_extended: String,
    val country: String,
    val cross_street: String,
    val dma: String,
    val formatted_address: String,
    val locality: String,
    val neighborhood: List<String>,
    val postcode: String,
    val region: String
)