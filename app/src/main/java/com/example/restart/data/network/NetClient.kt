package com.example.restart.data.network

import android.util.Log
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse
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

    suspend fun getCurrentWeather(location: String, lang: String = "en"): CurrentResponse? {
        val de : Deferred<CurrentResponse> = mApiService.getCurrentWeatherAsync(location, lang)
        return try {
            val entry = de.await()
            entry
        } catch (e : Exception) {
            if (e is HttpException) {
                Log.i("zhy", "is http ex ${e.response()?.code()}")
                Log.i("zhy", "http ex ${e.response()?.isSuccessful}")
            }
            null
        }
    }

    suspend fun getFutureWeather(location: String, days:Int = 7, lang: String = "en") : FutureResponse? {
        val de: Deferred<FutureResponse> = mApiService.getFutureWeatherAsync(location, days, lang)
        return try {
            val entry = de.await()
            entry
        } catch (e : Exception) {
            if (e is HttpException) {
                Log.i("zhy", "is http ex ${e.response()?.code()}")
                Log.i("zhy", "http ex ${e.response()?.isSuccessful}")
            }
            null
        }
    }

}