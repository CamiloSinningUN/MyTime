package com.example.mytime

import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.LinearProgressIndicator


class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var webView: WebView
        lateinit var layout: ConstraintLayout
        lateinit var cardViewLayout: ConstraintLayout
        lateinit var cardViewLayoutStatus: ConstraintLayout
        lateinit var linearIndicator: LinearProgressIndicator
        lateinit var textStatus: TextView
        lateinit var restArray: ArrayList<LinearProgressIndicator>
        lateinit var fab: FloatingActionButton
        lateinit var activityReference: Activity

        fun sendNotification(context: Context,notificationTitle:String,notificationText:String){
            var builder = NotificationCompat.Builder(context, "My notification")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }
        }
    }

    lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()

        fab.setOnClickListener {
            if (user.myState == State.Inactive) {
                user.setStudyingState()
            } else if (user.myState == State.Resting || user.myState == State.Studying) {
                areYouSureToStop()
            }
        }
    }

    //Mejorar estos textos
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My notification"
            val descriptionText = "idk"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("My notification", name, importance)
            mChannel.description = descriptionText
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun initialize() {
        webView = findViewById(R.id.webView)
        fab = findViewById(R.id.floatingActionButton)
        layout = findViewById(R.id.constraintLayout)
        cardViewLayout = findViewById(R.id.cardViewLayout)
        linearIndicator = findViewById(R.id.linearProgressIndicator)
        textStatus = findViewById(R.id.textView)
        cardViewLayoutStatus = findViewById(R.id.statusCardView)
        activityReference = this
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        restArray = ArrayList(4)
        restArray.add(findViewById(R.id.rest1))
        restArray.add(findViewById(R.id.rest2))
        restArray.add(findViewById(R.id.rest3))
        restArray.add(findViewById(R.id.rest4))
        createNotificationChannel()
        user = User(this)
        user.setInactiveState()
    }

    private fun areYouSureToStop() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detener")
        builder.setMessage("¿Está seguro que desea dejar de Estudiar?")
        builder.setPositiveButton("Sí") { dialog, which ->
            user.setInactiveState()
        }
        builder.setNegativeButton("No") { dialog, which ->
        }
        builder.show()
    }





}