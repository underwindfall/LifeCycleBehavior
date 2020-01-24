package com.qifan.lifecyclebehavior

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class PresenterBehavior<V, P>(
    val presenter: P,
    private val contract: Contract<V>
) : LifecycleObserver where V : BaseView, P : BasePresenter<V> {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        presenter.takeView(contract.getView())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        presenter.dropView()
    }

    interface Contract<V> {
        fun getView(): V
    }
}