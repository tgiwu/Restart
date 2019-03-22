package com.example.restart.data

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import java.lang.Exception

class NetClient private constructor(apiService: WeatherAPIService) {

    var mApiService = apiService

    companion object {
        @Volatile
        var instance: NetClient? = null

        fun getInstance(apiService: WeatherAPIService) =
            instance ?: synchronized(this) {
                NetClient(apiService).also {
                    instance = it
                }
            }
    }

    suspend fun getCurrentWeather(location: String, lang: String = "en"): WeatherEntry {

        val de : Deferred<WeatherEntry> = mApiService.getCurrentWeatherAsync(location, lang)
        return try {
            val entry = de.await()
            entry.isSuccess = true
            entry
        } catch (e : Exception) {
            if (e is HttpException) {
                Log.i("zhy", "is http ex ${e.response()?.code()}")
                Log.i("zhy", "http ex ${e.response()?.isSuccessful}")
            }
            WeatherEntry(null, null, e.message, false)
        }
    }

}