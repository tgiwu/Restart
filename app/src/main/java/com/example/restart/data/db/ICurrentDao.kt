package com.example.restart.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restart.data.db.entry.CurrentEntry
import com.example.restart.data.current.ImperialCurrentWeatherEntry
import com.example.restart.data.current.MetricCurrentWeatherEntry

@Dao
interface ICurrentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentEntry: CurrentEntry)

    @Query("select * from current_weather where id = 0")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = 0")
    fun getWeatherImperial() : LiveData<ImperialCurrentWeatherEntry>
}