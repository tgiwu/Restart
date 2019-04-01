package com.example.restart.ui

import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.internal.lazyDeferred
import com.example.restart.data.provider.IUnitProvider

class CurrentViewModel(
    private val repository: IWeatherRepository,
    unitSystem: IUnitProvider
) : WeatherViewModelBase(repository, unitSystem) {

    val weather by lazyDeferred {
        repository.getCurrentWeather(isMetricUnit)
    }


}
