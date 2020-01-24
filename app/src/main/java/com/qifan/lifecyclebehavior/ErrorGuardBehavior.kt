package com.qifan.lifecyclebehavior

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.qifan.lifecyclebehavior.helper.ErrorGuardEvent

class ErrorGuardBehavior(private val contract: Contract) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        ErrorGuardEvent.register()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        ErrorGuardEvent.unRegister()
    }

    interface Contract
}