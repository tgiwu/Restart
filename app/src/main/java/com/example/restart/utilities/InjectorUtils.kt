package com.example.restart.utilities

import com.example.restart.data.quote.FakeDataBase
import com.example.restart.data.quote.QuoteRepository
import com.example.restart.ui.QuotesViewModelFactory

object InjectorUtils {
    fun provideQuoteViewModelFactory() : QuotesViewModelFactory {
        val quoteRepository = QuoteRepository.getInstance(FakeDataBase.getInstance().quoteDao)
        return QuotesViewModelFactory(quoteRepository)
    }

}