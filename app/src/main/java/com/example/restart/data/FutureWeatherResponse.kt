package com.example.restart.data

import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    @SerializedName("current")
    val currentEntry: CurrentEntry = CurrentEntry(),
    val forecast: Forecast = Forecast(),
    val location: Location = Location(),
    var error : Throwable? = null
)