package com.example.restart.ui

import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.internal.lazyDeferred
import com.example.restart.data.provider.IUnitProvider
import org.threeten.bp.LocalDate

class FutureViewModel(
    private val weatherRepository: IWeatherRepository,
    unitSystem: IUnitProvider
) : WeatherViewModelBase(weatherRepository, unitSystem) {
    val weatherEntries by lazyDeferred {
        weatherRepository.getFutureWeather(isMetricUnit, LocalDate.now())
    }
}
