package com.example.restart.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restart.R
import com.example.restart.data.future.MetricFutureWeatherEntry
import com.example.restart.data.future.UnitSpecificFutureWeatherEntry
import kotlinx.android.synthetic.main.future_list_item.view.*
import org.threeten.bp.format.DateTimeFormatter

class FutureListAdapter(context: Context) : RecyclerView.Adapter<FutureListAdapter.Holder>() {
    private var forecast: List<out UnitSpecificFutureWeatherEntry> = ArrayList()
    private val mContext = context

    fun setEntries(entries: List<out UnitSpecificFutureWeatherEntry>) {
        forecast = entries
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.future_list_item, null)
        return Holder(view)
    }

    override fun getItemCount(): Int = forecast.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val unitAbbreviation = if (forecast[position] is MetricFutureWeatherEntry) "°C" else "°F"
        holder.temperature.text = "${forecast[position].avgTemperature} $unitAbbreviation"
        holder.condition.text = forecast[position].condition
        holder.date.text = forecast[position].date.format(DateTimeFormatter.ISO_DATE)
        Glide.with(mContext).load("http:${forecast[position].conditionIconUrl}").into(holder.conditionImg)
    }

    inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val temperature = v.item_temperature_txt
        val condition = v.item_condition_txt
        val date = v.item_date
        val conditionImg = v.item_condition_img
    }
}