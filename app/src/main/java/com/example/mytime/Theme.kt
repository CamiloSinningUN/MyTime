package com.example.mytime

import android.content.Context
import android.graphics.Color
import android.webkit.WebView

class Theme {

    var windowColor:Int = 0
    var layoutColor:Int = 0
    var cardViewLayoutColor: Int = 0
    var textColor:Int = 0
    lateinit var text:String
    var cardViewLayoutStatusColor: Int = 0


    fun inactiveTheme() {
        windowColor = Color.parseColor("#1C1519")
        layoutColor = Color.parseColor("#1C1519")
        cardViewLayoutColor = Color.parseColor("#454545")
        MainActivity.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        textColor = Color.parseColor("#CED0CE")
        text = "Inactivo"
        cardViewLayoutStatusColor = Color.parseColor("#454545")
        gifInactive(MainActivity.webView)
        changeTheme()
    }

    fun studyTheme(){
        windowColor = Color.parseColor("#12664f")
        layoutColor = Color.parseColor("#12664f")
        cardViewLayoutColor = Color.parseColor("#307473")
        textColor = Color.parseColor("#c6d4ff")
        text = "Estudiando"
        cardViewLayoutStatusColor = Color.parseColor("#307473")
        MainActivity.fab.setImageResource(R.drawable.ic_baseline_stop_24)
        gifStudy(MainActivity.webView)
        changeTheme()
    }

    fun restTheme(){
        windowColor = Color.parseColor("#13293d")
        layoutColor = Color.parseColor("#13293d")
        cardViewLayoutColor = Color.parseColor("#2a628f")
        cardViewLayoutStatusColor = Color.parseColor("#2a628f")
        MainActivity.fab.setImageResource(R.drawable.ic_baseline_stop_24)
        textColor = Color.parseColor("#4DFFF3")
        text = "Descansando"
        gifRest(MainActivity.webView)
        changeTheme()
    }

    fun changeTheme(){
        MainActivity.activityReference.window.statusBarColor = windowColor
        MainActivity.layout.setBackgroundColor(layoutColor)
        MainActivity.cardViewLayout.setBackgroundColor(cardViewLayoutColor)
        MainActivity.cardViewLayoutStatus.setBackgroundColor(cardViewLayoutStatusColor)
        MainActivity.textStatus.setTextColor(textColor)
        MainActivity.textStatus.setText(text)
    }



    fun gifStudy(webView: WebView){
        val ws = webView.settings
        ws.javaScriptEnabled = true
        val file = "file:android_asset/study2.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }

    fun gifInactive(webView: WebView){
        val ws = webView.settings
        ws.javaScriptEnabled = true
        val file = "file:android_asset/inactive.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }
    fun gifRest(webView: WebView){
        val ws = webView.settings
        ws.javaScriptEnabled = true
        val file = "file:android_asset/pequeño.gif"
        ws.loadWithOverviewMode = true;
        ws.useWideViewPort = true;
        webView.loadUrl(file)
    }

}