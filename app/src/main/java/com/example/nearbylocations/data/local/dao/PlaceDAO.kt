package com.example.nearbylocations.data.local.dao

import androidx.room.*
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.pojo.PlaceDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDAO {

    @Query("SELECT * FROM places")
    fun getAllPlaces(): Flow<List<PlaceItem>>

    @Query("SELECT * FROM places WHERE fsqId LIKE :fsqId")
    fun getPlaceInfo(fsqId: String): Flow<PlaceItem>

    @Query("UPDATE places SET placeDetail = :placeDetail WHERE fsqId = :fsqId")
    fun savePlaceInfo(fsqId: String, placeDetail: PlaceDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<PlaceItem>)

    @Query("DELETE FROM places")
    suspend fun deleteAllPlaces()
}
