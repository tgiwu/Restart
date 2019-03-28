package com.example.restart.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.restart.R
import com.example.restart.utilities.InjectorUtils
import kotlinx.android.synthetic.main.quote_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class QuoteActivity : AppCompatActivity(), CoroutineScope {
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
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
                    stringBuilder.append("${quote.quote} - ${quote.author}\n\n")
                }
                textView_quotes.text = stringBuilder.toString()
        })


        button_add.setOnClickListener {

            viewModel.refreshQuotes(coroutineContext)


//            viewModel.getWeather(coroutineContext)

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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
