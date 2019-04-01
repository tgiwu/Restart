package com.example.restart.data.provider

import android.content.Context
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class FetchTimeProviderImpl(context: Context): PreferenceProvider(context), IFetchTimeProvider {
    override fun getCurrentFetchTime(): LocalDateTime {
        val date = preferences.getString("current", null)
        return if (null == date) LocalDateTime.now().minusDays(1) else LocalDateTime.parse(date)
    }

    override fun saveCurrentFetchTime(date: LocalDateTime) {
        preferences.edit().putString("current", date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply()
    }

    override fun getFutureFetchTime(): LocalDateTime {
        val date = preferences.getString("future", null)
        return if (null == date) LocalDateTime.now().minusDays(1) else LocalDateTime.parse(date)
    }

    override fun saveFutureFetchTime(date: LocalDateTime) {
        preferences.edit().putString("future", date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).apply()
    }
}