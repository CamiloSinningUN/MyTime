package com.example.mytime

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton
    private lateinit var layout: ConstraintLayout
    private lateinit var cardViewLayout: ConstraintLayout
    private lateinit var linearIndicator: LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        restTime()

        fab.setOnClickListener {
            studyTime()
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
    }

    fun studyTheme(){
        layout.setBackgroundColor(Color.parseColor("#12664f"))
        cardViewLayout.setBackgroundColor(Color.parseColor("#307473"))
        fab.setBackgroundColor(Color.parseColor("#7a82ab"))
        gifStudy(webView)
    }
    fun restTime(){
        restTheme()
    }
    fun restTheme(){
        gifRest(webView)
    }

    fun gifRest(webView: WebView){
        var ws = webView.getSettings()
        ws.setJavaScriptEnabled(true)
        var file = "file:android_asset/pequeño.gif"
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        webView.loadUrl(file)
    }

    fun gifStudy(webView: WebView){
        var ws = webView.getSettings()
        ws.setJavaScriptEnabled(true)
        var file = "file:android_asset/study2.gif"
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        webView.loadUrl(file)
    }


}