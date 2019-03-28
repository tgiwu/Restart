package com.example.restart.data

import androidx.lifecycle.LiveData

interface IWeatherNetworkDataSource {

    val downloadCurrentWeather:LiveData<WeatherEntryResponse>
    val downloadFutureWeather:LiveData<FutureWeatherResponse>
    suspend fun getWeather(location: String, lang: String)
    suspend fun getFuture(location: String, days: Int, lang: String)
}