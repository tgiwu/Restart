package com.example.restart.ui

import android.util.Log
import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.internal.lazyDeferred
import com.example.restart.data.provider.IUnitProvider

class CurrentViewModel(
    private val repository: IWeatherRepository,
    unitSystem: IUnitProvider
) : WeatherViewModelBase(repository, unitSystem) {

    val weather by lazyDeferred {
        Log.i("zhy", "get currentEntry weather")
        repository.getCurrentWeather(isMetricUnit)
    }


}
