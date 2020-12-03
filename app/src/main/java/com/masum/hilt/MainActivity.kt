package com.masum.hilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureTimeMillis

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
            getFakeresponse()

        }
    }

    private fun getFakeresponse() {
        CoroutineScope(IO).launch {
            val executionTime = measureTimeMillis {
                //async for parallel execution
                //return the result
                val result1: Deferred<String> = async {
                    println("debug: launching job1${Thread.currentThread().name}")
                    getResult1FromApi()
                }

                val result2: Deferred<String> = async {
                    println("debug: launching job2${Thread.currentThread().name}")
                    getResult2FromApi()
                }
                //await here waiting for the result to execute
                setTextOnMainThread(result1.await())
                setTextOnMainThread(result2.await())
            }
            println("debug: total time elapsed:${executionTime}")
        }

    }

    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1700)
        return RESULT_2
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