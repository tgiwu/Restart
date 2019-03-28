package com.example.restart.data

import android.util.Log
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import kotlin.Exception

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

    suspend fun getCurrentWeather(location: String, lang: String = "en"): WeatherEntryResponse {
        val de : Deferred<WeatherEntryResponse> = mApiService.getCurrentWeatherAsync(location, lang)
        return try {
            val entry = de.await()
            entry
        } catch (e : Exception) {
            if (e is HttpException) {
                Log.i("zhy", "is http ex ${e.response()?.code()}")
                Log.i("zhy", "http ex ${e.response()?.isSuccessful}")
            }
            WeatherEntryResponse(error = e)
        }
    }

    suspend fun getFutureWeather(location: String, days:Int = 7, lang: String = "en") : FutureWeatherResponse {
        val de: Deferred<FutureWeatherResponse> = mApiService.getFutureWeatherAsync(location, days, lang)
        return try {
            val entry = de.await()
            entry
        } catch (e : Exception) {
            if (e is HttpException) {
                Log.i("zhy", "is http ex ${e.response()?.code()}")
                Log.i("zhy", "http ex ${e.response()?.isSuccessful}")
            }
            FutureWeatherResponse(error = e)
        }
    }

}