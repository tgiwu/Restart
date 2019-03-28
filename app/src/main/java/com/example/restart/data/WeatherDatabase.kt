package com.example.restart.data

import android.content.Context
import androidx.room.*

@Database(
    entities = [CurrentEntry::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): IFakeWeatherDao
    abstract fun futureWeatherDao():IFutureWeatherDao
    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, WeatherDatabase::class.java, "weather.db").build()
    }
}