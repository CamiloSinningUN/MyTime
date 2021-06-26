package com.example.mytime

import android.content.Context
import android.os.CountDownTimer
import androidx.core.view.isVisible
import com.example.mytime.MainActivity.Companion.linearIndicator
import com.example.mytime.MainActivity.Companion.restArray
import com.example.mytime.MainActivity.Companion.sendNotification

class User(context: Context) : Person(context) {
    //var myFriends: ArrayList<Friend>
    lateinit var myTimer: CountDownTimer
    var timerLength:Long = 1500
    val restDuration:Long = 300
    val studyDuration:Long = 1500
    var restTimes = 0

    override fun setInactiveState() {
        myState = State.Inactive
        Theme.inactive()
        reset()
    }

    override fun setStudyingState() {
        myState = State.Studying
        Theme.study()
        putLinearIndicatorIndeterminate(false)
        timerLength = studyDuration
        sendNotification(context, "Estudia apura", "Ha comenzado la estudiacion")
        startTimer()
    }

    override fun setRestingState() {
        myState = State.Resting
        Theme.rest()
        restTimes += 1
        myTimer.cancel()
        var notificationText:String
        var notificationTitle:String
        if(restTimes<4){
            notificationText = "Ya puedes dejar de matarte carnal"
            notificationTitle = "Descansación corta"
            var i = 1
            restArray.get(restTimes-1).progress = 100
            timerLength = restDuration
        }else{
            notificationText = "Tienes media hora para mimir"
            notificationTitle = "Descansación larga"
            reset()
            timerLength = studyDuration
            putLinearIndicatorIndeterminate(true)

        }
        sendNotification(context,notificationTitle,notificationText)
        startTimer()
    }

    fun reset(){
        myTimer.cancel()
        for(r in restArray){
            r.progress = 0
        }
        restTimes = 0
        linearIndicator.progress = 0
    }

    fun timerFinished(){
        if(myState == State.Studying){
            setRestingState()
        }else if(myState == State.Resting){
            setStudyingState()
        }
    }

    fun startTimer(){
        myTimer = object:CountDownTimer(timerLength*1000,1000){
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
        linearIndicator.progress = (secondsNow*100/timerLength).toInt()
    }

    fun putLinearIndicatorIndeterminate(sw:Boolean){
        linearIndicator.isVisible = false
        linearIndicator.isIndeterminate = sw
        linearIndicator.isVisible = true
    }
}