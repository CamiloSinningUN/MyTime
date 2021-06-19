package com.example.mytime

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator



class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var webView: WebView
        lateinit var fab: FloatingActionButton
        lateinit var layout: ConstraintLayout
        lateinit var cardViewLayout: ConstraintLayout
        lateinit var cardViewLayoutStatus: ConstraintLayout
        lateinit var linearIndicator: LinearProgressIndicator
        lateinit var textStatus: TextView
        lateinit var restArray: ArrayList<LinearProgressIndicator>
        lateinit var activityReference: Activity
    }
    lateinit var actionBar: ActionBar
    var state: State = State(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        initialize()
        state.inactiveTime()
        fab.setOnClickListener {
            if(state.myState == States.Inactive){
                state.studyTime()
            }else if(state.myState == States.Resting || state.myState == States.Studying){
                areYouSureToStop()
            }
        }
    }

    fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My notification"
            val descriptionText = "idk"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("My notification",name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun initialize(){
        webView = findViewById(R.id.web_view)
        fab = findViewById(R.id.floatingActionButton2)
        layout = findViewById(R.id.constraint_layout)
        cardViewLayout = findViewById(R.id.cardViewLayout)
        linearIndicator = findViewById(R.id.linearProgressIndicator2)
        textStatus = findViewById(R.id.textView)
        cardViewLayoutStatus = findViewById(R.id.status)

        actionBar = supportActionBar!!
        actionBar.hide()

        activityReference = this

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        restArray = ArrayList<LinearProgressIndicator>(4)
        restArray.add(findViewById(R.id.progressBar5))
        restArray.add(findViewById(R.id.progressBar6))
        restArray.add(findViewById(R.id.progressBar7))
        restArray.add(findViewById(R.id.progressBar8))

        createNotificationChannel()
    }

    private fun areYouSureToStop() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detener")
        builder.setMessage("¿Está seguro que desea dejar de Estudiar?")

        builder.setPositiveButton("Sí") { dialog, which ->
            state.inactiveTime()
        }

        builder.setNegativeButton("No") { dialog, which ->
        }
        builder.show()
    }



}