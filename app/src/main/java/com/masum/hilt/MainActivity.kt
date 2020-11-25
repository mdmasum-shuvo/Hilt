package com.masum.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    var RESULT_1 = "result1"
    var RESULT_2 = "result2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnSubmit = findViewById(R.id.btn_submit) as Button
        textView = findViewById(R.id.text) as TextView
        btnSubmit.setOnClickListener {
            CoroutineScope(IO).launch {
                getFakeresponse()
            }
        }
    }

    private suspend fun getFakeresponse() {
        var result1 = getResult1FromApi()
        setTextOnMainThread(result1)
        var result2 = getResult2FromApi(result1)
        setTextOnMainThread(result2)
    }

    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromApi(result1: String): String {
        logThread("getResult2FromApi")
        delay(1000)
        return result1 + RESULT_2
    }

    private fun logThread(methodName: String) {
        println("debug:${methodName} : ${Thread.currentThread().name}")
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setText(input)
        }
    }

    private fun setText(input: String) {
        var newText = textView.text.toString() + "\n${input}"
        textView.text = newText
    }
}