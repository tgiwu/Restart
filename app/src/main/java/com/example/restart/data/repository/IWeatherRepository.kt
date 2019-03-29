package com.example.restart.data.repository

import androidx.lifecycle.LiveData
import com.example.restart.data.current.UnitSpecificCurrentWeather
import com.example.restart.data.future.UnitSpecificFutureWeatherEntry
import org.threeten.bp.LocalDate

interface IWeatherRepository {
    suspend fun getCurrentWeather(metric:Boolean) : LiveData<out UnitSpecificCurrentWeather>
    suspend fun getFutureWeather(metric: Boolean, date: LocalDate) : LiveData<out List<UnitSpecificFutureWeatherEntry>>
    suspend fun getFutureWeatherByDay(metric: Boolean, date: LocalDate):LiveData<out UnitSpecificFutureWeatherEntry>
}