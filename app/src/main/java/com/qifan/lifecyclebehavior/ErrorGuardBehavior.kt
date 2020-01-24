package com.qifan.lifecyclebehavior

import com.qifan.lifecyclebehavior.helper.ErrorGuardEvent

class ErrorGuardBehavior(private val contract: Contract) {
    fun onCreate() {
        ErrorGuardEvent.register()
    }

    fun onDestroy() {
        ErrorGuardEvent.unRegister()
    }

    interface Contract
}