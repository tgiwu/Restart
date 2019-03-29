package com.example.restart.data.network.response

import com.example.restart.data.db.entry.CurrentEntry
import com.example.restart.data.db.entry.Location
import com.google.gson.annotations.SerializedName

data class CurrentResponse(
    @SerializedName("current")
    val currentEntry: CurrentEntry,
    val location: Location
)