package com.example.mytime


import android.content.Context
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible

enum class States {
    Studying, Resting, Inactive
}

class State(var context: Context){
    var myState: States  =  States.Inactive

    lateinit var timer: CountDownTimer
    var timerLength:Long = 1500
    val restDuration:Long = 300
    val studyDuration:Long = 1500
    var restTimes = 0

    var theme: Theme = Theme()

    lateinit var notificationText:String
    lateinit var notificationTitle:String


    fun inactiveTime() {
        theme.inactiveTheme()
        if(myState == States.Studying || myState == States.Resting){
            timer.cancel()
            resetRestArray()
        }
        myState = States.Inactive
    }

    fun studyTime(){
        notificationText = "Ha comenzado la estudiacion"
        notificationTitle = "Estudia apura"
        sendNotification()
        theme.studyTheme()
        putLinearIndicatorIndeterminate(false)
        timerLength = studyDuration
        myState = States.Studying
        startTimer()
    }

    fun restTime(){

        restTimes = restTimes+1
        theme.restTheme()
        timer.cancel()
        if(restTimes<4){
            notificationText = "Ya puedes dejar de matarte carnal"
            notificationTitle = "Descansación corta"
            var i = 1
            for(r in MainActivity.restArray){
                r.progress = 100
                if(i == restTimes){
                    break
                }
                i += 1
            }
            timerLength = restDuration
        }else{
            notificationText = "Tienes media hora para mimir"
            notificationTitle = "Descansación larga"
            resetRestArray()
            timerLength = studyDuration
            putLinearIndicatorIndeterminate(true)

        }
        sendNotification()
        myState = States.Resting
        startTimer()
    }

    fun resetRestArray(){
        for(r in MainActivity.restArray){
            r.progress = 0
        }
        restTimes = 0
        MainActivity.linearIndicator.progress = 0
    }


    fun putLinearIndicatorIndeterminate(sw:Boolean){
        MainActivity.linearIndicator.isVisible = false
        MainActivity.linearIndicator.isIndeterminate = sw
        MainActivity.linearIndicator.isVisible = true
    }

    fun timerFinished(){
        if(myState == States.Studying){
                restTime()
        }else if(myState == States.Resting){
                studyTime()
        }
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

    private fun updateCountDownUI(secondsNow: Long) {
        MainActivity.linearIndicator.progress = (secondsNow*100/timerLength).toInt()
    }

    fun sendNotification(){
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