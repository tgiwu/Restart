package com.example.restart.data.db.entry

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "future_weather", indices = [Index(value = ["date"],  unique = true)])
data class FutureEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
//    @Embedded
//    val astro: Astro,
    val date: String,
//    @SerializedName("date_epoch")
//    val dateEpoch: Int = 0,
    @Embedded
    val day: Day
) {
    override fun toString(): String {
        return "FutureEntry(id=$id, date='$date', day=$day)"
    }
}