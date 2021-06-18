package com.example.mytime

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var cardViewLayoutStatus: ConstraintLayout
    private lateinit var linearIndicator: LinearProgressIndicator
    private lateinit var actionBar: ActionBar
    private lateinit var textStatus: TextView

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
        setSupportActionBar(findViewById(R.id.my_toolbar))
        initialize()
        inactiveTime()

        fab.setOnClickListener {
            if(state == State.Inactive){
                studyTime()
            }else if(state == State.Resting || state == State.Studying){
                areYouSureToStop()

            }
        }
    }

    private fun areYouSureToStop() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detener")
        builder.setMessage("¿Está seguro que desea dejar de Estudiar?")

        builder.setPositiveButton("Sí") { dialog, which ->
            inactiveTime()
        }

        builder.setNegativeButton("No") { dialog, which ->
        }
        builder.show()


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
        window.statusBarColor = Color.parseColor("#1C1519")
        layout.setBackgroundColor(Color.parseColor("#1C1519"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#454545"))
        fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        textStatus.setTextColor(Color.parseColor("#CED0CE"))
        textStatus.setText("Inactivo")
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#454545")))
        cardViewLayoutStatus.setBackgroundColor(Color.parseColor("#454545"))
        gifInactive(webView)
    }

    fun studyTheme(){
        window.statusBarColor = Color.parseColor("#12664f")
        layout.setBackgroundColor(Color.parseColor("#12664f"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#307473"))
        textStatus.setTextColor(Color.parseColor("#c6d4ff"))
        textStatus.setText("Estudiando")
        cardViewLayoutStatus.setBackgroundColor(Color.parseColor("#307473"))
        fab.setImageResource(R.drawable.ic_baseline_stop_24)
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#307473")))
        gifStudy(webView)
    }

    fun restTheme(){
        window.statusBarColor = Color.parseColor("#13293d")
        layout.setBackgroundColor(Color.parseColor("#13293d"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#2a628f"))
        cardViewLayoutStatus.setBackgroundColor(Color.parseColor("#2a628f"))
        fab.setImageResource(R.drawable.ic_baseline_stop_24)
        textStatus.setTextColor(Color.parseColor("#4DFFF3"))
        textStatus.setText("Descansando")
        actionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#2a628f")))
        gifRest(webView)
    }

    fun initialize(){
        webView = findViewById(R.id.web_view)
        fab = findViewById(R.id.floatingActionButton2)
        layout = findViewById(R.id.constraint_layout)
        cardViewLayout = findViewById(R.id.cardViewLayout)
        linearIndicator = findViewById(R.id.linearProgressIndicator2)
        actionBar = supportActionBar!!
        actionBar.hide()
        textStatus = findViewById(R.id.textView)
        cardViewLayoutStatus = findViewById(R.id.status)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
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

    fun gifInactive(webView: WebView){
        var ws = webView.settings
        ws.javaScriptEnabled = true
        var file = "file:android_asset/inactive.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }


}