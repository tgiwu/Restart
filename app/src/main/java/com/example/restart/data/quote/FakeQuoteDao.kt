package com.example.restart.data.quote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restart.data.Quote

class FakeQuoteDao {
    private val quoteList = mutableListOf<Quote>()
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.value = quoteList
        System.loadLibrary("native-lib")
    }

    fun  addQuote(quote: Quote) {
        quoteList.add(Quote(textEncrypt(quote.quote), textEncrypt(quote.author)))
        quotes.value = quoteList
    }

    fun getQuotesAsLiveData() = quotes as LiveData<List<Quote>>

    fun refreshQuotes(lists : List<Quote>)  {

        quoteList.clear()
        for (quote in lists) {
            quoteList.add(Quote(textEncrypt(quote.quote), textEncrypt(quote.author)))
        }
        quotes.value = quoteList
    }

    external fun textEncrypt(str: String): String
    external fun textDecrypt(str: String): String

}