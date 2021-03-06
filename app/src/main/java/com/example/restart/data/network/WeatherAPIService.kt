package com.example.restart.data.network

import android.util.Log
import com.example.restart.data.network.response.CurrentResponse
import com.example.restart.data.network.response.FutureResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {

    @GET("current")
    fun getCurrentWeatherAsync(@Query("query") location: String,
                               @Query("language") lang: String = "en"): Deferred<CurrentResponse>

    @GET("forecast")
    fun getFutureWeatherAsync(@Query("query") location: String,
                              @Query("days") days: Int,
                              @Query("lang") lang: String = "en"):Deferred<FutureResponse>

    companion object {
//        private const val API_KEY = "569d88f9730d4026b7d60206191903"
        private const val API_KEY = "6cc861320246b555c94f361f946ffc5a"
        private const val WEATHER_BASE_URL = "http://api.weatherstack.com/"

        operator fun invoke(): WeatherAPIService {

            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val logger = HttpLoggingInterceptor.Logger { message -> Log.i("httpLog", " http log $message") }
            val logInterceptor = HttpLoggingInterceptor(logger)
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addNetworkInterceptor(logInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(WEATHER_BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherAPIService::class.java)
        }
    }
}