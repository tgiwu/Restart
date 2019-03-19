package com.example.restart

import android.app.Application
import com.example.restart.data.NetClient
import com.example.restart.data.WeatherAPIService

class App: Application() {

    companion object {
        private var application: App? = null
        @Volatile
        private var netClient: NetClient ?= null
        fun getInstance(): App? = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    fun getClient() = netClient ?: synchronized(this) {
        NetClient.getInstance(WeatherAPIService()).also {
            netClient = it
        }
    }

}