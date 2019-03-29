package com.example.restart.data.network

import androidx.lifecycle.LiveData
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse

interface IWeatherNetworkDataSource {

    val downloadCurrentWeather:LiveData<CurrentResponse>
    val downloadFutureWeather:LiveData<FutureResponse>
    suspend fun getWeather(location: String, lang: String)
    suspend fun getFuture(location: String, days: Int, lang: String)
}