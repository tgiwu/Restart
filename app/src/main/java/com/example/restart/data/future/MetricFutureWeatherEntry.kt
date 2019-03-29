package com.example.restart.data.future

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

data class MetricFutureWeatherEntry(
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempC")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val condition: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificFutureWeatherEntry {
    override fun toString(): String {
        return "MetricFutureWeatherEntry(date=$date, avgTemperature=$avgTemperature, condition='$condition', conditionIconUrl='$conditionIconUrl')"
    }
}