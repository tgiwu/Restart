package com.example.restart.data.network.response

import com.example.restart.data.db.entry.Location
import com.google.gson.annotations.SerializedName

data class FutureResponse(
    @SerializedName("forecast")
    val forecastDaysContainer: ForecastDaysContainer,
    val location: Location
) {
    override fun toString(): String {
        return "FutureResponse(forecastDaysContainer=$forecastDaysContainer, location=$location)"
    }
}