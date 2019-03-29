package com.example.restart.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse
import java.lang.Exception

const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDataSourceImpl(private val apiService: WeatherAPIService) :
    IWeatherNetworkDataSource {

    private val _downloadFutureWeather = MutableLiveData<FutureResponse>()
    override val downloadFutureWeather: LiveData<FutureResponse>
        get() = _downloadFutureWeather

    private val _downloadCurrentWeather = MutableLiveData<CurrentResponse>()
    override val downloadCurrentWeather: LiveData<CurrentResponse>
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
            val result = apiService.getFutureWeatherAsync(location, days = FORECAST_DAYS_COUNT, lang = lang).await()
            _downloadFutureWeather.postValue(result)
        } catch (e : Exception) {
            Log.e("zhy", "exception message ${e.message}")
        }
    }

}