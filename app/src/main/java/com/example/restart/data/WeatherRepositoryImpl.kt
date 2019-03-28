package com.example.restart.data

import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl (
    private val currentWeatherDao: IFakeWeatherDao,
    private val weatherNetworkDataSourceImpl: WeatherNetworkDataSourceImpl
) : IWeatherRepository {
    init {
        weatherNetworkDataSourceImpl.downloadCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<ImperialCurrentWeatherEntry> {
        initWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeatherImperial()
        }
    }

    suspend fun getFuture(location: String, days: Int) {
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(System.currentTimeMillis())) fetchCurrentWeather()
    }

    private fun persistFetchedCurrentWeather(weather: WeatherEntryResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            weather.currentEntry?.let {
                currentWeatherDao.upsert(it)
            }
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSourceImpl.getWeather("beijing", "zh")
    }

    private fun isFetchCurrentNeeded(lastFetchTime: Long) : Boolean {
//        return (System.currentTimeMillis() - lastFetchTime > 30 * 60 * 1000)
        return true
    }

}