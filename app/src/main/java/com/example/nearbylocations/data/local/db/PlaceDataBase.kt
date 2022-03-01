package com.example.nearbylocations.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nearbylocations.data.local.dao.PlaceDAO

@Database(entities = [PlaceItem::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlaceDataBase : RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PlaceDataBase? = null
        fun getInstance(context: Context): PlaceDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PlaceDataBase {
            return Room.databaseBuilder(context, PlaceDataBase::class.java, "place_database")
                // TODO: NOT FOR PRODUCTION!!!
                .fallbackToDestructiveMigrationOnDowngrade()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
