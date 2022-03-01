package com.example.nearbylocations.data.local.repository

import com.example.nearbylocations.data.local.dao.FavoriteMovieDAO
import javax.inject.Inject

interface LocalRepository {

}

class LocalRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDAO
) : LocalRepository {

}