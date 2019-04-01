package com.example.restart.data.provider

import org.threeten.bp.LocalDateTime


interface IFetchTimeProvider {
    fun getCurrentFetchTime(): LocalDateTime
    fun saveCurrentFetchTime(date: LocalDateTime)
    fun getFutureFetchTime():LocalDateTime
    fun saveFutureFetchTime(date: LocalDateTime)
}