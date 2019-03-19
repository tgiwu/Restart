package com.example.restart.net

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors


private val executor = Executors.newCachedThreadPool()

private val uiHandler = Handler(Looper.getMainLooper())


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

