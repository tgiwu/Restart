package com.example.restart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.restart.data.Quote
import com.example.restart.data.QuoteRepository
import com.example.restart.net.FakeNetworkCall
import com.example.restart.net.FakeNetworkError
import com.example.restart.net.FakeNetworkSuccess
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getQuotes() = quoteRepository.getQuote()

    fun addQuote(quote: Quote) = uiScope.launch(Dispatchers.Main) {
        quoteRepository.addQuote(quote)
    }

     suspend fun getQuotesA() : LiveData<List<Quote>>  =
         withContext(uiScope.coroutineContext) {
             quoteRepository.getQuote()
         }

    suspend fun <T> FakeNetworkCall<T>.await() : T {
        return suspendCoroutine { continuation ->
            addOnResultListener { result ->
                when(result) {
                    is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                    is FakeNetworkError<T> -> continuation.resumeWithException(result.error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}