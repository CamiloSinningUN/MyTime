package com.example.mytime

import android.content.Context

enum class State {
    Studying, Resting, Inactive
}

abstract class Person(var context: Context) {

    lateinit var myState: State

    abstract fun setInactiveState()

    abstract fun setStudyingState()

    abstract fun setRestingState()
}