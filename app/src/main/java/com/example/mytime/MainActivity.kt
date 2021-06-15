package com.example.mytime

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator

enum class State {
    Studying, Resting
}

enum class TimerState{
    Stopped,Paused,Running
}
class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private lateinit var layout: ConstraintLayout
    private lateinit var cardViewLayout: ConstraintLayout
    private lateinit var linearIndicator: LinearProgressIndicator

    private var state: State = State.Resting

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stopped
    private var secondsRemaining = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        restTime()

        fab.setOnClickListener {
            if(state == State.Resting){
                studyTime()
                state = State.Studying
            }else if(state == State.Studying){
                restTime()
                state = State.Resting
            }
        }
    }

    fun initialize(){
        webView = findViewById(R.id.web_view)
        fab = findViewById(R.id.floatingActionButton2)
        layout = findViewById(R.id.constraintLayout)
        cardViewLayout = findViewById(R.id.cardViewLayout)
        linearIndicator = findViewById(R.id.linearProgressIndicator2)
    }

    fun studyTime(){
        studyTheme()
        startTimer()
        timerState = TimerState.Running
    }

    fun studyTheme(){
        layout.setBackgroundColor(Color.parseColor("#12664f"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#307473"))
        fab.setImageResource(R.drawable.ic_baseline_stop_24)
        gifStudy(webView)
    }
    fun restTime(){
        restTheme()
        timer.cancel()
        timerState = TimerState.Stopped
        onTimerFinished()
    }

    override fun onResume(){
        super.onResume()
        initTimer()
    }

    fun restTheme(){
        layout.setBackgroundColor(Color.parseColor("#13293d"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#2a628f"))
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        gifRest(webView)
    }

    fun gifRest(webView: WebView){
        var ws = webView.getSettings()
        ws.setJavaScriptEnabled(true)
        var file = "file:android_asset/pequeño.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }

    fun gifStudy(webView: WebView){
        var ws = webView.getSettings()
        ws.setJavaScriptEnabled(true)
        var file = "file:android_asset/study2.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }


}