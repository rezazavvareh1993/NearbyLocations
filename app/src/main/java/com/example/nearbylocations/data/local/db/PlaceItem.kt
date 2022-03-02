package com.example.nearbylocations.data.local.db

import androidx.room.*
import com.example.nearbylocations.pojo.PlaceDetail
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import javax.inject.Inject

@Entity(tableName = "places")
class PlaceItem(
    @PrimaryKey val fsqId: String,
    val name: String? = null,
    val address: String? = null,
    val icon: String? = null,
    val distance: String? = null,
    val placeDetail: PlaceDetail? = null
)

class Converters {

    private inline fun <reified T> convertToJson(obj: T): String {
        return Gson().toJson(obj)
    }

    private inline fun <reified T> convertFromJson(json: String): T? {
        if (json == "{}") {
            return null
        }
        return Gson().fromJson(json, T::class.java)
    }

    @TypeConverter
    fun placeDetailToString(placeDetail: PlaceDetail?): String =
        convertToJson(placeDetail)

    @TypeConverter
    fun stringToPlaceDetail(placeDetailJson: String): PlaceDetail? =
        convertFromJson(placeDetailJson)
}
