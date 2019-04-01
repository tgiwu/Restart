package com.example.restart.data.repository

import androidx.lifecycle.LiveData
import com.example.restart.data.network.FORECAST_DAYS_COUNT
import com.example.restart.data.network.WeatherNetworkDataSourceImpl
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.current.UnitSpecificCurrentWeather
import com.example.restart.data.db.ICurrentDao
import com.example.restart.data.db.IFutureDao
import com.example.restart.data.network.response.FutureResponse
import com.example.restart.data.future.UnitSpecificFutureWeatherEntry
import com.example.restart.data.provider.IFetchTimeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

class WeatherRepositoryImpl(
    private val currentWeatherDao: ICurrentDao,
    private val futureWeatherDao: IFutureDao,
    private val weatherNetworkDataSourceImpl: WeatherNetworkDataSourceImpl,
    private val fetchTimeProvider: IFetchTimeProvider
) : IWeatherRepository {

    init {
        weatherNetworkDataSourceImpl.downloadCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }

        weatherNetworkDataSourceImpl.downloadFutureWeather.observeForever { newFutureWeather ->
            persistFetchedFutureWeather(newFutureWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeather> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric)
                currentWeatherDao.getWeatherMetric()
            else
                currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getFutureWeather(
        metric: Boolean,
        date: LocalDate
    ): LiveData<out List<UnitSpecificFutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric)
                futureWeatherDao.getSimpleWeatherForecastMetric(date)
            else
                futureWeatherDao.getSimpleWeatherForecastImperial(date)
        }
    }

    override suspend fun getFutureWeatherByDay(
        metric: Boolean,
        date: LocalDate
    ): LiveData<out UnitSpecificFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext if (metric)
                futureWeatherDao.getFutureWeatherByDateMetric(date)
            else
                futureWeatherDao.getFutureWeatherByDateImperial(date)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded()) fetchCurrentWeather()
        if (isFetchFutureNeeded()) fetchFutureWeather()
    }

    private fun persistFetchedCurrentWeather(weather: CurrentResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            fetchTimeProvider.saveCurrentFetchTime(LocalDateTime.now())
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
        weatherNetworkDataSourceImpl.getFuture("beijing", FORECAST_DAYS_COUNT, "zh")
    }

    private fun isFetchCurrentNeeded(): Boolean {
        return fetchTimeProvider.getCurrentFetchTime().plusMinutes(60).isBefore(LocalDateTime.now())
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

}