package com.example.mytime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    //private var background: ConstraintLayout = findViewById(R.id.constraintLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.web_view)
        var ws = webView.getSettings()
        ws.setJavaScriptEnabled(true)
        var file = "file:android_asset/pequeño.gif"
        ws.setLoadWithOverviewMode(true);
        ws.setUseWideViewPort(true);
        webView.loadUrl(file)
    }
}