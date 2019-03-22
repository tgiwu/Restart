package com.example.restart.net

import com.example.restart.data.Quote
import com.example.restart.data.WeatherEntry
import kotlinx.coroutines.runBlocking

object Iapis {
    fun refreshQuotes() : FakeNetworkCall<List<Quote>> = requestRefreshQuote()

    fun getWeather() : FakeNetworkCall<WeatherEntry> = runBlocking {  requestWeather() }
}