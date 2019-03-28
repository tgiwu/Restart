package com.example.restart.data

import android.os.Build
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(str:String?) = str?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
        } else {
            null
        }
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: LocalDate?) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    } else {
        null
    }
}