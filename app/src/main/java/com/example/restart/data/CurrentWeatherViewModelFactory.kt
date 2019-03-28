package com.example.restart.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restart.ui.CurrentViewModel

class CurrentWeatherViewModelFactory(private val weatherRepository: IWeatherRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentViewModel(weatherRepository) as T
    }
}