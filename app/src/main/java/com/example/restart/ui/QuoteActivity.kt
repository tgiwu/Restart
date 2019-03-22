package com.example.restart.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.restart.App
import com.example.restart.R
import com.example.restart.data.Quote
import com.example.restart.net.Iapis
import com.example.restart.utilities.InjectorUtils
import kotlinx.android.synthetic.main.quote_main.*
import java.lang.StringBuilder

class QuoteActivity : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(this).inflate(R.layout.quote_main, null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            view.addOnUnhandledKeyEventListener { _, _ ->
                return@addOnUnhandledKeyEventListener true
            }
        }
        setContentView(view)
        initializeUi()

    }

    private fun initializeUi() {
        val factory = InjectorUtils.provideQuoteViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)

        viewModel.quotes.observe(this, Observer {
            quotes ->
                val stringBuilder = StringBuilder()
                quotes.forEach { quote ->
                    stringBuilder.append("${textDecrypt(quote.quote)} - ${textDecrypt(quote.author)}\n\n")
                }
                textView_quotes.text = stringBuilder.toString()
        })


        button_add.setOnClickListener {

            viewModel.refreshQuotes()

            viewModel.getWeather()

//            if (editText_quotes.text.isNotEmpty() && editText_author.text.isNotEmpty()) {
//                val quote = Quote(textEncrypt(editText_quotes.text.toString()), textEncrypt(editText_author.text.toString()))
//
//                viewModel.addQuote(quote)
//
//                editText_quotes.setText("")
//                editText_author.setText("")
//            }
        }
    }

    external fun sayHello() : String

    external fun textDecrypt(str: String): String
}
