package com.example.restart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.data.provider.IUnitProvider

class FutureListWeatherViewModelFactory(
    private val weatherRepository: IWeatherRepository,
    private val unitProvider: IUnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureViewModel(weatherRepository, unitProvider) as T
    }
}