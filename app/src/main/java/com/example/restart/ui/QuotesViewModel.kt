package com.example.restart.ui

import androidx.lifecycle.ViewModel
import com.example.restart.data.Quote
import com.example.restart.data.QuoteRepository

class QuotesViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    fun getQuotes() = quoteRepository.getQuote()

    fun addQuote(quote: Quote) = quoteRepository.addQuote(quote)
}