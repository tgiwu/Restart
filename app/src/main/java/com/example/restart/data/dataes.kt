package com.example.restart.data

import com.google.gson.annotations.SerializedName

data class Quote(val quote: String, val author: String) {
    override fun toString(): String {
        return "$quote  -  $author"
    }
}

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val tzId: String
) {
    override fun toString(): String {
        return "Location(country='$country', lat=$lat, localtime='$localtime', localtimeEpoch=$localtimeEpoch, lon=$lon, name='$name', region='$region', tzId='$tzId')"
    }
}

data class Current(
    val cloud: Int,
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    val uv: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double
) {
    override fun toString(): String {
        return "Current(cloud=$cloud, condition=$condition, feelslikeC=$feelslikeC, feelslikeF=$feelslikeF, gustKph=$gustKph, gustMph=$gustMph, humidity=$humidity, isDay=$isDay, lastUpdated='$lastUpdated', lastUpdatedEpoch=$lastUpdatedEpoch, precipIn=$precipIn, precipMm=$precipMm, pressureIn=$pressureIn, pressureMb=$pressureMb, tempC=$tempC, tempF=$tempF, uv=$uv, visKm=$visKm, visMiles=$visMiles, windDegree=$windDegree, windDir='$windDir', windKph=$windKph, windMph=$windMph)"
    }
}

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
) {
    override fun toString(): String {
        return "Condition(code=$code, icon='$icon', text='$text')"
    }
}

data class WeatherEntry(
    val current: Current?,
    val location: Location?,
    var msg : String? = null,
    var isSuccess : Boolean = true
) {
    override fun toString(): String {
        return "WeatherEntry(current=$current, location=$location)"
    }
}