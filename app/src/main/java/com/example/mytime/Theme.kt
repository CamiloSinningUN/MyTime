package com.example.mytime

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewAnimationUtils
import android.webkit.WebView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mytime.MainActivity.Companion.activityReference
import com.example.mytime.MainActivity.Companion.cardViewLayout
import com.example.mytime.MainActivity.Companion.cardViewLayoutStatus
import com.example.mytime.MainActivity.Companion.fab
import com.example.mytime.MainActivity.Companion.notes
import com.example.mytime.MainActivity.Companion.notestext
import com.example.mytime.MainActivity.Companion.textStatus
import com.example.mytime.MainActivity.Companion.layout
import com.example.mytime.MainActivity.Companion.textArea
import com.example.mytime.MainActivity.Companion.webView


class Theme {
    companion object {
        var windowColor: Int = 0
        var cardViewLayoutColor: Int = 0
        var textColor: Int = 0
        lateinit var text: String


        fun inactive(
        ) {
            windowColor = Color.parseColor("#1C1519")
            cardViewLayoutColor = Color.parseColor("#454545")
            fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            textColor = Color.parseColor("#CED0CE")
            text = "Inactivo"
            gifInactive(webView)
            changeTheme()
        }

        fun study(
        ) {
            windowColor = Color.parseColor("#12664f")
            cardViewLayoutColor = Color.parseColor("#307473")
            textColor = Color.parseColor("#c6d4ff")
            text = "Estudiando"
            fab.setImageResource(R.drawable.ic_baseline_stop_24)
            gifStudy(webView)
            changeTheme()
        }

        fun rest(
        ) {
            windowColor = Color.parseColor("#13293d")
            cardViewLayoutColor = Color.parseColor("#2a628f")
            fab.setImageResource(R.drawable.ic_baseline_stop_24)
            textColor = Color.parseColor("#4DFFF3")
            text = "Descansando"
            gifRest(webView)
            changeTheme()
        }

        fun changeTheme(
        ) {
            val cx = MainActivity.anim.width / 2
            val cy = MainActivity.anim.height / 2
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(
                MainActivity.anim,
                fab.x.toInt() + fab.width / 2, fab.y.toInt() + fab.height / 2, 0f, finalRadius
            )
            anim.setDuration(150)
            MainActivity.anim.visibility = View.VISIBLE

            anim.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    cardViewLayout.setBackgroundColor(cardViewLayoutColor)
                    cardViewLayoutStatus.setBackgroundColor(cardViewLayoutColor)
                    notes.setBackgroundColor(cardViewLayoutColor)
                    notestext.setTextColor(textColor)
                    textStatus.setTextColor(textColor)
                    textStatus.setText(text)
                    textArea.setTextColor(textColor)
                    MainActivity.anim.setCardBackgroundColor(windowColor)
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    MainActivity.anim.visibility = View.INVISIBLE
                    activityReference.window.statusBarColor = windowColor
                    layout.setBackgroundColor(windowColor)

                }
            })
            anim.start()
        }


        fun gifStudy(webView: WebView) {
            val ws = webView.settings
            ws.javaScriptEnabled = true
            val file = "file:android_asset/study2.gif"
            ws.loadWithOverviewMode = true;
            ws.useWideViewPort = true;
            webView.loadUrl(file)
        }

        fun gifInactive(webView: WebView) {
            val ws = webView.settings
            ws.javaScriptEnabled = true
            val file = "file:android_asset/inactive.gif"
            ws.loadWithOverviewMode = true;
            ws.useWideViewPort = true;
            webView.loadUrl(file)
        }

        fun gifRest(webView: WebView) {
            val ws = webView.settings
            ws.javaScriptEnabled = true
            val file = "file:android_asset/pequeño.gif"
            ws.loadWithOverviewMode = true;
            ws.useWideViewPort = true;
            webView.loadUrl(file)
        }
    }


}