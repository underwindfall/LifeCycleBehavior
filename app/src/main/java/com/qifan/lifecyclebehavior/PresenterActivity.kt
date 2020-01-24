package com.qifan.lifecyclebehavior

abstract class PresenterActivity<V, P>(protected val presenter: P) :
    ErrorGuardActivity() where V : BaseView, P : BasePresenter<V> {

    override fun onResume() {
        super.onResume()
        presenter.takeView(getView())
    }

    abstract fun getView(): V

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }

}