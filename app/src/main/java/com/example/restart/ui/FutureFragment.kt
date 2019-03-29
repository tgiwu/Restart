package com.example.restart.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.restart.R
import com.example.restart.data.future.ImperialFutureWeatherEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.future_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class FutureFragment : ScopedFragment(), KodeinAware {
    override val kodein by kodein()

    private val viewModelFactory : FutureListWeatherViewModelFactory by instance()
    private lateinit var viewModel: FutureViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureViewModel::class.java)
       bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()

        futureWeatherEntries.observe(this@FutureFragment, Observer { weatherEntries ->
            if (null == weatherEntries) return@Observer

            future_text.text = Gson().toJson(weatherEntries, object : TypeToken<List<ImperialFutureWeatherEntry>>() {}.type)

        })
    }

}
