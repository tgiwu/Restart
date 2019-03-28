package com.example.restart.ui

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.example.restart.data.IWeatherRepository
import com.example.restart.data.lazyDeferred

class CurrentViewModel(
    private val repository: IWeatherRepository
) : ViewModel() {
    val isMetric: Boolean
        get() = true
    val weather by lazyDeferred {
        Log.i("zhy", "get current weather")
        repository.getCurrentWeather(isMetric)
    }


}
