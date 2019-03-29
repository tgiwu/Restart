package com.example.restart.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restart.data.db.entry.FutureEntry
import com.example.restart.data.future.ImperialFutureWeatherEntry
import com.example.restart.data.future.MetricFutureWeatherEntry
import org.threeten.bp.LocalDate

@Dao
interface IFutureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<FutureEntry>)

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastMetric(startDate: LocalDate) : LiveData<List<MetricFutureWeatherEntry>>

    @Query("select * from future_weather where date(date) >= date(:startDate)")
    fun getSimpleWeatherForecastImperial(startDate: LocalDate) : LiveData<List<ImperialFutureWeatherEntry>>

    @Query("select count(id) from future_weather where date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: LocalDate) : Int

    @Query("delete from future_weather where date(date) < date(:firstDateToKeep)")
    fun deleteOldEntries(firstDateToKeep: LocalDate)

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getFutureWeatherByDateMetric(date: LocalDate): LiveData<MetricFutureWeatherEntry>

    @Query("select * from future_weather where date(date) = date(:date)")
    fun getFutureWeatherByDateImperial(date: LocalDate) : LiveData<ImperialFutureWeatherEntry>
}