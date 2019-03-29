package com.example.restart.ui

import androidx.lifecycle.ViewModel
import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.internal.UnitSystem
import com.example.restart.data.provider.IUnitProvider

abstract class WeatherViewModelBase(private val weatherRepository: IWeatherRepository, unitProvider: IUnitProvider) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit : Boolean
    get() = unitSystem == UnitSystem.METRIC


}