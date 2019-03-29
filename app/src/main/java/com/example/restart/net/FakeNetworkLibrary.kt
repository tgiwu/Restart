package com.example.restart.net

import android.os.Handler
import android.os.Looper
import com.example.restart.data.quote.Quote
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine



private val uiHandler = Handler(Looper.getMainLooper())


suspend fun requestRefreshQuote(): FakeNetworkCall<List<Quote>> {
    val result = FakeNetworkCall<List<Quote>>()

    delay(3000)

    val list = ArrayList<Quote>()
    for (i in 0..3) {
        val quote = Quote("quote", "author")
        list.add(quote)
    }
    result.onSuccess(list)

    return result
}

suspend fun requestCurrentWeather(): FakeNetworkCall<CurrentResponse> {
    val result = FakeNetworkCall<CurrentResponse>()
//    val entry = App.getInstance()?.getClient()?.getCurrentWeather("beijing")
//    if (null == entry?.error) {
//        result.onSuccess(entry!!)
//    } else {
//        result.onError(entry.error!!)
//    }
    return result
}

suspend fun requestFutureWeather(location: String = "beijing", days: Int = 7, lang: String="en") : FakeNetworkCall<FutureResponse> {
    val result = FakeNetworkCall<FutureResponse>()
//    val entry= App.getInstance()?.getClient()?.getFutureWeather(location, days, lang)
//    if (null == entry?.error) {
//        result.onSuccess(entry!!)
//    } else {
//        result.onError(entry.error!!)
//    }
    return result
}

class FakeNetworkCall<T> {
    var result: FakeNetworkResult<T>? = null
    private val listeners = mutableListOf<FakeNetworkListener<T>>()

    fun addOnResultListener(listener: FakeNetworkListener<T>) {
        trySendResult(listener)
        listeners += listener
    }

    fun onSuccess(data: T) {
        result = FakeNetworkSuccess<T>(data)
        sendResultToAllListeners()
    }

    fun onError(throwable: Throwable) {
        result = FakeNetworkError(throwable)
        sendResultToAllListeners()
    }

    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    private fun trySendResult(listener: FakeNetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}

class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()
class FakeNetworkError<T>(val error: Throwable) : FakeNetworkResult<T>()

sealed class FakeNetworkResult<T>

typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

class FakeNetworkException(message: String) : Throwable(message)

suspend fun <T> FakeNetworkCall<T>.await(): T {
    return suspendCoroutine { continuation ->
        addOnResultListener { result ->
            when (result) {
                is FakeNetworkSuccess<T> -> continuation.resume(result.data)
                is FakeNetworkError<T> -> continuation.resumeWithException(result.error)
            }
        }
    }
}

