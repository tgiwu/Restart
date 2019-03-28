package com.example.restart

import android.app.Application
import android.content.Context
import com.example.restart.data.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { WeatherAPIService() }
        bind<IWeatherRepository>() with singleton { WeatherRepositoryImpl(instance(), instance()) }
        bind<WeatherNetworkDataSourceImpl>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
