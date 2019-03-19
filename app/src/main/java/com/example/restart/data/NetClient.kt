package com.example.restart.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NetClient private constructor(apiService: WeatherAPIService) {

    var mApiService = apiService

    companion object {
        @Volatile
        var instance: NetClient? = null

        fun getInstance(apiService: WeatherAPIService) =
            instance ?: synchronized(this) {
                NetClient(apiService).also {
                    instance = it
                }
            }
    }

    fun getCurrentWeather(location: String, lang: String = "en") {
        GlobalScope.launch(Dispatchers.Default) {
            val currentWeatherResponse = mApiService.getCurrentWeatherAsync(location, lang)
            Log.i("zhy", "text = $currentWeatherResponse")
        }
    }

}