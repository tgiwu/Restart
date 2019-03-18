package com.example.restart.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.restart.R
import com.example.restart.data.Quote
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
        setContentView(R.layout.quote_main)
        initializeUi()
        Log.i("restart", sayHello())
    }

    private fun initializeUi() {
        val factory = InjectorUtils.provideQuoteViewModelFactory()
        val viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)

        viewModel.getQuotes().observe(this, Observer {
            quotes ->
                val stringBuilder = StringBuilder()
                quotes.forEach { quote ->
                    stringBuilder.append("$quote\n\n")
                }
                textView_quotes.text = stringBuilder.toString()
        })

        button_add.setOnClickListener {
            if (editText_quotes.text.isNotEmpty() && editText_author.text.isNotEmpty()) {
                val quote = Quote(editText_quotes.text.toString(), editText_author.text.toString())

                val encrypted = textEncrypt(editText_quotes.text.toString())

                Log.i("zhy", "encrypt ${editText_quotes.text} to $encrypted")
                Log.i("zhy", "decrypt $encrypted to ${textDecrypt(encrypted)}")
                viewModel.addQuote(quote)

                editText_quotes.setText("")
                editText_author.setText("")
            }
        }
    }

    external fun sayHello() : String
    external fun textEncrypt(str: String): String
    external fun textDecrypt(str: String): String
}
