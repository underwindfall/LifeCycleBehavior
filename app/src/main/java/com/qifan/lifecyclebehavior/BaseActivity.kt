package com.qifan.lifecyclebehavior

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.qifan.lifecyclebehavior.behavior.BehaviorContainer
import com.qifan.lifecyclebehavior.behavior.BehaviorSubscriber
import com.qifan.lifecyclebehavior.behavior.bindContainerToLifecycle

abstract class BaseActivity : AppCompatActivity(), BehaviorSubscriber {

    override val behaviorContainer: BehaviorContainer by bindContainerToLifecycle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    @LayoutRes
    abstract fun getLayout(): Int
}