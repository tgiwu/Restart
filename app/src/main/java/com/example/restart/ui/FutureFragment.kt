package com.example.restart.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.restart.R
import com.example.restart.data.future.ImperialFutureWeatherEntry
import com.example.restart.ui.adapter.FutureListAdapter
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
    private var mAdapter:FutureListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FutureViewModel::class.java)

        mAdapter?:also {
            val layoutManager = LinearLayoutManager(context)
            layoutManager.orientation = RecyclerView.VERTICAL
            forecast_list_view.layoutManager = LinearLayoutManager(context)
            mAdapter = FutureListAdapter(context!!)
            forecast_list_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            forecast_list_view.adapter = mAdapter
        }

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()

        futureWeatherEntries.observe(this@FutureFragment, Observer { weatherEntries ->
            if (null == weatherEntries) return@Observer

            mAdapter?.setEntries(weatherEntries)

        })
    }

}
