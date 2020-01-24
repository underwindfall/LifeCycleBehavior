package com.qifan.lifecyclebehavior

class PresenterBehavior<V, P>(
    val presenter: P,
    private val contract: Contract<V>
) where V : BaseView, P : BasePresenter<V> {
    fun onResume() {
        presenter.takeView(contract.getView())
    }

    fun onPause() {
        presenter.dropView()
    }

    interface Contract<V> {
        fun getView(): V
    }
}