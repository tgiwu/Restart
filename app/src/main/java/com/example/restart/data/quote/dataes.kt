package com.example.restart.data.quote

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Quote(val quote: String, val author: String) {
    override fun toString(): String {
        return "$quote  -  $author"
    }
}
//
//data class Location(
//    val country: String = "",
//    val lat: Double = 0.0,
//    val localtime: String = "",
//    @SerializedName("localtime_epoch")
//    val localtimeEpoch: Int = 0,
//    val lon: Double = 0.0,
//    val name: String = "",
//    val region: String = "",
//    @SerializedName("tz_id")
//    val tzId: String = ""
//) {
//    override fun toString(): String {
//        return "Location(country='$country', lat=$lat, localtime='$localtime', localtimeEpoch=$localtimeEpoch, lon=$lon, name='$name', region='$region', tzId='$tzId')"
//    }
//}
//
//@Entity(tableName = "current_weather")
//data class CurrentEntry(
//    val cloud: Int = 0,
//    @Embedded(prefix = "condition_")
//    val condition: Condition = Condition(),
//    @SerializedName("feelslike_c")
//    val feelslikeC: Double = 0.0,
//    @SerializedName("feelslike_f")
//    val feelslikeF: Double = 0.0,
//    @SerializedName("gust_kph")
//    val gustKph: Double = 0.0,
//    @SerializedName("gust_mph")
//    val gustMph: Double = 0.0,
//    val humidity: Int = 0,
//    @SerializedName("is_day")
//    val isDay: Int = 0,
//    @SerializedName("last_updated")
//    val lastUpdated: String = "",
//    @SerializedName("last_updated_epoch")
//    val lastUpdatedEpoch: Int = 0,
//    @SerializedName("precip_in")
//    val precipIn: Double = 0.0,
//    @SerializedName("precip_mm")
//    val precipMm: Double = 0.0,
//    @SerializedName("pressure_in")
//    val pressureIn: Double = 0.0,
//    @SerializedName("pressure_mb")
//    val pressureMb: Double = 0.0,
//    @SerializedName("temp_c")
//    val tempC: Double = 0.0,
//    @SerializedName("temp_f")
//    val tempF: Double = 0.0,
//    val uv: Double = 0.0,
//    @SerializedName("vis_km")
//    val visKm: Double = 0.0,
//    @SerializedName("vis_miles")
//    val visMiles: Double = 0.0,
//    @SerializedName("wind_degree")
//    val windDegree: Int = 0,
//    @SerializedName("wind_dir")
//    val windDir: String = "",
//    @SerializedName("wind_kph")
//    val windKph: Double = 0.0,
//    @SerializedName("wind_mph")
//    val windMph: Double = 0.0
//) {
//    @PrimaryKey(autoGenerate = false)
//    var id: Int = 0
//    override fun toString(): String {
//        return "CurrentEntry(cloud=$cloud, condition=$condition, feelslikeC=$feelslikeC, feelslikeF=$feelslikeF, gustKph=$gustKph, gustMph=$gustMph, humidity=$humidity, isDay=$isDay, lastUpdated='$lastUpdated', lastUpdatedEpoch=$lastUpdatedEpoch, precipIn=$precipIn, precipMm=$precipMm, pressureIn=$pressureIn, pressureMb=$pressureMb, tempC=$tempC, tempF=$tempF, uv=$uv, visKm=$visKm, visMiles=$visMiles, windDegree=$windDegree, windDir='$windDir', windKph=$windKph, windMph=$windMph)"
//    }
//}
//
//data class Condition(
//    val code: Int = 0,
//    val icon: String = "",
//    val text: String = ""
//) {
//    override fun toString(): String {
//        return "Condition(code=$code, icon='$icon', text='$text')"
//    }
//}
//
//data class WeatherEntryResponse(
//    @SerializedName("currentEntry")
//    val currentEntry: CurrentEntry? = null,
//    val location: Location? = null,
//    var error: Throwable? = null
//) {
//    override fun toString(): String {
//        return "WeatherEntryResponse(currentEntry=$currentEntry, location=$location)"
//    }
//}
//data class Day(
//    val avghumidity: Double = 0.0,
//    @SerializedName("avgtemp_c")
//    val avgtempC: Double = 0.0,
//    @SerializedName("avgtemp_f")
//    val avgtempF: Double = 0.0,
//    @SerializedName("avgvis_km")
//    val avgvisKm: Double = 0.0,
//    @SerializedName("avgvis_miles")
//    val avgvisMiles: Double = 0.0,
//    @Embedded(prefix = "condition_")
//    val condition: Condition = Condition(),
//    @SerializedName("maxtemp_c")
//    val maxtempC: Double = 0.0,
//    @SerializedName("maxtemp_f")
//    val maxtempF: Double = 0.0,
//    @SerializedName("maxwind_kph")
//    val maxwindKph: Double = 0.0,
//    @SerializedName("maxwind_mph")
//    val maxwindMph: Double = 0.0,
//    @SerializedName("mintemp_c")
//    val mintempC: Double = 0.0,
//    @SerializedName("mintemp_f")
//    val mintempF: Double = 0.0,
//    @SerializedName("totalprecip_in")
//    val totalprecipIn: Double = 0.0,
//    @SerializedName("totalprecip_mm")
//    val totalprecipMm: Double = 0.0,
//    val uv: Double = 0.0
//) {
//    override fun toString(): String {
//        return "Day(avghumidity=$avghumidity, avgtempC=$avgtempC, avgtempF=$avgtempF, avgvisKm=$avgvisKm, avgvisMiles=$avgvisMiles, condition=$condition, maxtempC=$maxtempC, maxtempF=$maxtempF, maxwindKph=$maxwindKph, maxwindMph=$maxwindMph, mintempC=$mintempC, mintempF=$mintempF, totalprecipIn=$totalprecipIn, totalprecipMm=$totalprecipMm, uv=$uv)"
//    }
//}
//
//data class Astro(
//    val moonrise: String = "",
//    val moonset: String = "",
//    val sunrise: String = "",
//    val sunset: String = ""
//) {
//    override fun toString(): String {
//        return "Astro(moonrise='$moonrise', moonset='$moonset', sunrise='$sunrise', sunset='$sunset')"
//    }
//}
//
//@Entity(tableName = "future_weather", indices = [Index(value = ["date"],  unique = true)])
//data class FutureEntry(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    @Embedded
//    val astro: Astro = Astro(),
//    val date: String = "",
//    @SerializedName("date_epoch")
//    val dateEpoch: Int = 0,
//    @Embedded
//    val day: Day = Day()
//) {
//    override fun toString(): String {
//        return "FutureEntry(date='$date', dateEpoch=$dateEpoch, day=$day)"
//    }
//}
//
//data class ForecastDaysContainer(
//    val futureEntry: List<FutureEntry> = listOf()
//)