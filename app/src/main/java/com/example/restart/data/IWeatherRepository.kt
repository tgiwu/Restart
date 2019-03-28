package com.example.restart.data

import androidx.lifecycle.LiveData

interface IWeatherRepository {
    suspend fun getCurrentWeather(metric:Boolean) : LiveData<ImperialCurrentWeatherEntry>
}