package com.example.restart.data.network.response

import com.example.restart.data.db.entry.FutureEntry
import com.google.gson.annotations.SerializedName

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val futureEntry: List<FutureEntry>

) {
    override fun toString(): String {
        return "ForecastDaysContainer(futureEntry=$futureEntry)"
    }
}