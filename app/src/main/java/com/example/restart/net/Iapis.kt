package com.example.restart.net

import com.example.restart.data.Quote
import com.example.restart.data.WeatherEntry

object Iapis {
    suspend fun refreshQuotes() : FakeNetworkCall<List<Quote>> = requestRefreshQuote()

   suspend fun getWeather() : FakeNetworkCall<WeatherEntry> = requestWeather()
}