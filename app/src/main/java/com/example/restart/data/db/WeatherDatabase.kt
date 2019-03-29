package com.example.restart.data.db

import android.content.Context
import androidx.room.*
import com.example.restart.data.db.entry.CurrentEntry
import com.example.restart.data.db.entry.FutureEntry

@Database(
    entities = [CurrentEntry::class, FutureEntry::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): ICurrentDao
    abstract fun futureWeatherDao(): IFutureDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(
            LOCK
        ) {
            instance
                ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db").build()
    }
}