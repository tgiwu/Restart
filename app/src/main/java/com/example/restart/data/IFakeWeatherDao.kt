package com.example.restart.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IFakeWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentEntry: CurrentEntry)

    @Query("select * from current_weather where id = 0")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = 0")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}