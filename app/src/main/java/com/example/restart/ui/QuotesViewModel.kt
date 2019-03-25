package com.example.restart.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.restart.data.Quote
import com.example.restart.data.QuoteRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val quotes = quoteRepository.getQuoteLiveData()

    fun refreshQuotes(context: CoroutineContext) = CoroutineScope(context).launch {
        quoteRepository.refreshQuotes {
            when (it) {
                is QuoteRepository.RefreshState.Loading -> Log.i("zhy", "loading")
                is QuoteRepository.RefreshState.Success -> Log.i("zhy", "success ")
                is QuoteRepository.RefreshState.Error -> Log.i("zhy", "error ${it.error.message}")
            }
        }
    }

    fun getWeather(context: CoroutineContext) = CoroutineScope(context).launch {
        quoteRepository.getWeather {
            when (it) {
                is QuoteRepository.RefreshState.Loading -> Log.i("zhy", "weather loading")
                is QuoteRepository.RefreshState.Success -> Log.i("zhy", "weather success")
                is QuoteRepository.RefreshState.Error -> Log.i("zhy", "weather error ${it.error.message}")
            }
        }
    }

    fun addQuote(quote: Quote) = uiScope.launch(Dispatchers.Main) {
        quoteRepository.addQuote(quote)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}