package com.example.restart.net

import com.example.restart.data.Quote
import com.example.restart.data.WeatherEntryResponse

object Iapis {
    suspend fun refreshQuotes(): FakeNetworkCall<List<Quote>> = requestRefreshQuote()

    suspend fun getWeather(): FakeNetworkCall<WeatherEntryResponse> = requestCurrentWeather()

    suspend fun getFutureWeather(location: String, days: Int = 7, lang: String = "en") =
        requestFutureWeather(location, days, lang)
}