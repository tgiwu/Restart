package com.example.restart.net

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.restart.App
import com.example.restart.data.Quote
import com.example.restart.data.WeatherEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


private val executor = Executors.newCachedThreadPool()

private val uiHandler = Handler(Looper.getMainLooper())


fun requestRefreshQuote() : FakeNetworkCall<List<Quote>> {
    var result = FakeNetworkCall<List<Quote>>()

    executor.submit {
        Thread.sleep(3000)

        val list =  ArrayList<Quote>()
        for (i in 0..3) {
            val quote = Quote("quote", "author")
            list.add(quote)
        }
        result.onSuccess(list)
    }

    return result
}

 suspend fun requestWeather() : FakeNetworkCall<WeatherEntry>  = coroutineScope {
     val result = FakeNetworkCall<WeatherEntry>()
      val entry = withContext(Dispatchers.Default) {
          App.getInstance()?.getClient()?.getCurrentWeather("Paris")
      }
     entry?.let {
         Log.i("zhy", "isSuccess ${it.isSuccess}")
         if (it.isSuccess) {
             result.onSuccess(it)
         } else {
             Log.i("zhy", " msg ${it.msg}")
             result.onError(Throwable(it.msg))
         }
     }
     return@coroutineScope result
    }

class FakeNetworkCall<T> {
    var result: FakeNetworkResult<T>? = null
    val listeners = mutableListOf<FakeNetworkListener<T>>()

    fun addOnResultListener(listener : FakeNetworkListener<T>) {
        trySendResult(listener)
        listeners += listener
    }

    fun onSuccess(data : T) {
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
        thisResult?.let{
            uiHandler.post {
                listener(thisResult)
            }
        }
    }
}

class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()
class FakeNetworkError<T>(val error:Throwable) : FakeNetworkResult<T>()

sealed class FakeNetworkResult<T>

typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

class FakeNetworkException(message:String) : Throwable(message)

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

