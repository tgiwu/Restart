package com.example.restart.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class WeatherNetworkDataSourceImpl(private val apiService: WeatherAPIService) : IWeatherNetworkDataSource {

    private val _downloadFutureWeather = MutableLiveData<FutureWeatherResponse>()
    override val downloadFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadFutureWeather

    private val _downloadCurrentWeather = MutableLiveData<WeatherEntryResponse>()
    override val downloadCurrentWeather: LiveData<WeatherEntryResponse>
        get() = _downloadCurrentWeather

    override suspend fun getWeather(location: String, lang: String) {
        try {
            val result = apiService.getCurrentWeatherAsync(location, lang)
                .await()
            _downloadCurrentWeather.postValue(result)
        } catch (e: Exception) {
            Log.e("zhy", "exception message ${e.message}")
        }
    }

    override suspend fun getFuture(location: String, days: Int, lang: String) {
        try {
            val result = apiService.getFutureWeatherAsync(location, days, lang).await()
            _downloadFutureWeather.postValue(result)
        } catch (e : Exception) {
            Log.e("zhy", "exception message ${e.message}")
        }
    }

}