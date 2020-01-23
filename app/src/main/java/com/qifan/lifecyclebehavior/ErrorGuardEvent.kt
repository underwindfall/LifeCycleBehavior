package com.qifan.lifecyclebehavior

import android.util.Log

object ErrorGuardEvent {
    fun register() {
        Log.d("ErrorGuardEvent", "ErrorGuardEvent has been registered")
    }

    fun unRegister() {
        Log.d("ErrorGuardEvent", "ErrorGuardEvent has been  unregistered")
    }
}