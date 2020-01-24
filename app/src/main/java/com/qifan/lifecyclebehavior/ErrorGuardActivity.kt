package com.qifan.lifecyclebehavior

import android.os.Bundle
import com.qifan.lifecyclebehavior.helper.ErrorGuardEvent

abstract class ErrorGuardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ErrorGuardEvent.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        ErrorGuardEvent.unRegister()
    }
}