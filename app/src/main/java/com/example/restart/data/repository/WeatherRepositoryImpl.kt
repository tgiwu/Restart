package com.example.restart.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.restart.data.network.FORECAST_DAYS_COUNT
import com.example.restart.data.network.WeatherNetworkDataSourceImpl
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.current.UnitSpecificCurrentWeather
import com.example.restart.data.db.ICurrentDao
import com.example.restart.data.db.IFutureDao
import com.example.restart.data.network.response.FutureResponse
import com.example.restart.data.future.UnitSpecificFutureWeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate

class WeatherRepositoryImpl(
    private val currentWeatherDao: ICurrentDao,
    private val futureWeatherDao: IFutureDao,
    private val weatherNetworkDataSourceImpl: WeatherNetworkDataSourceImpl
) : IWeatherRepository {

    init {
        weatherNetworkDataSourceImpl.downloadCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }

        weatherNetworkDataSourceImpl.downloadFutureWeather.observeForever {newFutureWeather ->
            persistFetchedFutureWeather(newFutureWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
        initWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getFutureWeather(
        metric: Boolean,
        date: LocalDate
    ): LiveData<out List<UnitSpecificFutureWeatherEntry>> {
        initWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext futureWeatherDao.getSimpleWeatherForecastImperial(date)
        }
    }

    override suspend fun getFutureWeatherByDay(metric: Boolean, date: LocalDate): LiveData<out UnitSpecificFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext if (metric) futureWeatherDao.getFutureWeatherByDateMetric(date) else futureWeatherDao.getFutureWeatherByDateImperial(date)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(System.currentTimeMillis())) fetchCurrentWeather()
        if (isFetchFutureNeeded()) fetchFutureWeather()
    }

    private fun persistFetchedCurrentWeather(weather: CurrentResponse) {
        GlobalScope.launch(Dispatchers.IO) {
                currentWeatherDao.upsert(weather.currentEntry!!)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureResponse) {
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.forecastDaysContainer.futureEntry

            futureWeatherDao.insert(futureWeatherList)
        }
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSourceImpl.getWeather("beijing", "zh")
    }

    private suspend fun fetchFutureWeather() {
        weatherNetworkDataSourceImpl.getFuture("beijing", FORECAST_DAYS_COUNT,"zh")
    }

    private fun isFetchCurrentNeeded(lastFetchTime: Long): Boolean {
//        return (System.currentTimeMillis() - lastFetchTime > 30 * 60 * 1000)
        return true
    }

    private fun isFetchFutureNeeded() : Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

}