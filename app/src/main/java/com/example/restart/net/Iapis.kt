package com.example.restart.net

import com.example.restart.data.quote.Quote
import com.example.restart.data.network.response.CurrentResponse

object Iapis {
    suspend fun refreshQuotes(): FakeNetworkCall<List<Quote>> = requestRefreshQuote()

    suspend fun getWeather(): FakeNetworkCall<CurrentResponse> = requestCurrentWeather()

    suspend fun getFutureWeather(location: String, days: Int = 7, lang: String = "en") =
        requestFutureWeather(location, days, lang)
}