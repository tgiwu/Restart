package com.example.restart.data.future

import org.threeten.bp.LocalDate


interface UnitSpecificFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val condition:String
    val conditionIconUrl: String
}