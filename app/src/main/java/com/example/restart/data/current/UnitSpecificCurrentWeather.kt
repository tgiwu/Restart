package com.example.restart.data.current

interface UnitSpecificCurrentWeather {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: String
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}