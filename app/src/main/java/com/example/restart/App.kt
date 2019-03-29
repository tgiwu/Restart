package com.example.restart

import android.app.Application
import com.example.restart.data.db.WeatherDatabase
import com.example.restart.data.network.WeatherAPIService
import com.example.restart.data.network.WeatherNetworkDataSourceImpl
import com.example.restart.data.provider.IUnitProvider
import com.example.restart.data.provider.UnitProviderImpl
import com.example.restart.data.repository.IWeatherRepository
import com.example.restart.data.repository.WeatherRepositoryImpl
import com.example.restart.ui.CurrentWeatherViewModelFactory
import com.example.restart.ui.FutureListWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
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
        bind() from singleton { instance<WeatherDatabase>().futureWeatherDao() }
        bind() from singleton { WeatherAPIService() }
        bind<IWeatherRepository>() with singleton {
            WeatherRepositoryImpl(
                instance(),
                instance(),
                instance()
            )
        }
        bind<WeatherNetworkDataSourceImpl>() with singleton {
            WeatherNetworkDataSourceImpl(
                instance()
            )
        }
        bind<IUnitProvider>() with singleton {
            UnitProviderImpl(
                instance()
            )
        }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
//        PreferenceManager.setDefaultValues(this, R.)
    }
}
