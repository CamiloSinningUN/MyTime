package com.example.mytime

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator

enum class State {
    Studying, Resting, Inactive
}

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private lateinit var layout: ConstraintLayout
    private lateinit var cardViewLayout: ConstraintLayout
    private lateinit var linearIndicator: LinearProgressIndicator

    private lateinit var restArray: ArrayList<LinearProgressIndicator>

    private var state: State = State.Inactive

    private lateinit var timer: CountDownTimer
    private var timerLength:Long = 1500

    private var restTimes = 0

    private val restDuration:Long = 300
    private val studyDuration:Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        inactiveTime()

        fab.setOnClickListener {
            if(state == State.Inactive){
                studyTime()
            }else if(state == State.Resting || state == State.Studying){
                inactiveTime()
            }
        }
    }

    private fun inactiveTime() {
        inactiveTheme()
        if(state == State.Studying || state == State.Resting){
            timer.cancel()
            for(r in restArray){
                r.progress = 0
            }
            restTimes = 0
            linearIndicator.progress = 0
        }
        state = State.Inactive
    }

    fun studyTime(){
        studyTheme()
        linearIndicator.isIndeterminate = false
        timerLength = studyDuration
        state = State.Studying
        startTimer()
    }

    fun restTime(){
        restTimes = restTimes+1
        restTheme()
        timer.cancel()
        if(restTimes<4){
            var i = 1
            for(r in restArray){
                r.progress = 100
                if(i == restTimes){
                    break
                }
                i = i+1
            }
            timerLength = restDuration
        }else{
            for(r in restArray){
                r.progress = 0
            }
            restTimes = 0
            timerLength = studyDuration
            linearIndicator.isVisible = false
            linearIndicator.isIndeterminate = true
            linearIndicator.isVisible = true

        }
        state = State.Resting
        startTimer()
    }

    private fun inactiveTheme() {
        layout.setBackgroundColor(Color.parseColor("#1C1519"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#454545"))
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        gifRest(webView)
    }

    fun studyTheme(){
        layout.setBackgroundColor(Color.parseColor("#12664f"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#307473"))
        fab.setImageResource(R.drawable.ic_baseline_stop_24)
        gifStudy(webView)
    }

    fun restTheme(){
        layout.setBackgroundColor(Color.parseColor("#13293d"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#2a628f"))
        fab.setImageResource(R.drawable.ic_baseline_stop_24)
        gifRest(webView)
    }

    fun initialize(){
        webView = findViewById(R.id.web_view)
        fab = findViewById(R.id.floatingActionButton2)
        layout = findViewById(R.id.constraintLayout)
        cardViewLayout = findViewById(R.id.cardViewLayout)
        linearIndicator = findViewById(R.id.linearProgressIndicator2)
        restArray = ArrayList<LinearProgressIndicator>(4)
        restArray.add(findViewById(R.id.progressBar5))
        restArray.add(findViewById(R.id.progressBar6))
        restArray.add(findViewById(R.id.progressBar7))
        restArray.add(findViewById(R.id.progressBar8))


    }

    fun startTimer(){
        timer = object:CountDownTimer(timerLength*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                var secondsNow = timerLength- millisUntilFinished/1000
                updateCountDownUI(secondsNow)
            }

            override fun onFinish() {
                timerFinished()
            }

        }.start()

    }

    private fun timerFinished() {
        if(state == State.Studying){
            restTime()
        }else if(state == State.Resting){
            studyTime()
        }

    }

    private fun updateCountDownUI(secondsNow: Long) {
        linearIndicator.progress = (secondsNow*100/timerLength).toInt()
    }

    fun gifRest(webView: WebView){
        var ws = webView.settings
        ws.javaScriptEnabled = true
        var file = "file:android_asset/pequeño.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }

    fun gifStudy(webView: WebView){
        var ws = webView.settings
        ws.javaScriptEnabled = true
        var file = "file:android_asset/study2.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }


}