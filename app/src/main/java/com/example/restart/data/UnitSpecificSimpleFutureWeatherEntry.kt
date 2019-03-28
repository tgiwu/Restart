package com.example.restart.data

import java.time.LocalDate

interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val condition:String
    val conditionIconUrl: String
}