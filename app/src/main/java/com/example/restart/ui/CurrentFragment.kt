package com.example.restart.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.restart.R
import com.example.restart.data.current.ImperialCurrentWeatherEntry
import com.example.restart.data.current.MetricCurrentWeatherEntry
import com.example.restart.data.future.MetricFutureWeatherEntry
import kotlinx.android.synthetic.main.current_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CurrentFragment : ScopedFragment(), KodeinAware {
    override val kodein by kodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(this@CurrentFragment, Observer {
            if (it == null) return@Observer

            if (it is MetricCurrentWeatherEntry) {
                current_temperature_txt.text = "${it.temperature} °F"
                current_feel_like_txt.text = "${it.feelsLikeTemperature} °F"
               current_visibility_txt.text = "${it.visibilityDistance} miles"
                current_precipitation_txt.text = "${it.precipitationVolume} inch"

            } else if (it is ImperialCurrentWeatherEntry) {
                current_temperature_txt.text = "${it.temperature} °C"
                current_feel_like_txt.text = "${it.feelsLikeTemperature} °C"
                current_visibility_txt.text = "${it.visibilityDistance} km"
                current_precipitation_txt.text = "${it.precipitationVolume} mm"
            }
            current_wind_speed.text = "${it.windSpeed} kph"
            current_condition_txt.text = it.conditionText
            current_wind_direction_txt.text = it.windDirection
            Glide.with(this@CurrentFragment.activity!!).load("http:${it.conditionIconUrl}").into(current_condition_img)
        })
    }
}
