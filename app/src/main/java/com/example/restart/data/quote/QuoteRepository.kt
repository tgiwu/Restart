package com.example.restart.data.quote

import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse
//import com.example.restart.data.WeatherEntryResponse
import com.example.restart.net.FakeNetworkError
import com.example.restart.net.FakeNetworkSuccess
import com.example.restart.net.Iapis

class QuoteRepository private constructor(private val quoteDao: FakeQuoteDao){

    fun  addQuote(quote: Quote) {
        quoteDao.addQuote(quote)
    }

    fun getQuoteLiveData() = quoteDao.getQuotesAsLiveData()

    suspend fun refreshQuotes(onStateChange: StateListener) {
        onStateChange(RefreshState.Loading)
        val  call  = Iapis.refreshQuotes()
        call.addOnResultListener { result ->
            when(result) {
                is FakeNetworkSuccess<List<Quote>> ->{
                    quoteDao.refreshQuotes(result.data)
                    onStateChange(RefreshState.Success)
                }
                is FakeNetworkError<List<Quote>> -> {
                    onStateChange(RefreshState.Error(result.error))
                }
            }
        }
    }

    suspend fun getCurrentWeather(onStateChange: StateListener) {
        onStateChange(RefreshState.Loading)
        val call = Iapis.getWeather()
        call.addOnResultListener { result ->
            when(result) {
                is FakeNetworkSuccess<CurrentResponse> -> {
                    onStateChange(RefreshState.Success)
                }
                is FakeNetworkError<CurrentResponse> -> {
                    onStateChange(RefreshState.Error(result.error))
                }
            }
        }
    }

    suspend fun getFutureWeather(location:String, days:Int, lang:String,onStateChange: StateListener) {
        onStateChange(RefreshState.Loading)
        val call = Iapis.getFutureWeather(location, days, lang)
        call.addOnResultListener { result ->
            when(result) {
                is FakeNetworkSuccess<FutureResponse> -> {
                    onStateChange(RefreshState.Success)
                }
                is FakeNetworkError<FutureResponse> -> {
                    onStateChange(RefreshState.Error(result.error))
                }
            }
        }
    }

    sealed class RefreshState{
        object Loading : RefreshState()
        object Success : RefreshState()
        class Error(val error: Throwable) : RefreshState()
    }

    companion object {
        @Volatile private var instance : QuoteRepository? = null

        fun getInstance(quoteDao: FakeQuoteDao) =
            instance ?: synchronized(this) {
                QuoteRepository(quoteDao)
                    .also { instance = it }
            }
    }
}
typealias StateListener = (QuoteRepository.RefreshState) -> Unit
