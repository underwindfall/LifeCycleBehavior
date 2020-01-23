package com.qifan.lifecyclebehavior

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V, P>(protected val presenter: P) : AppCompatActivity() where V : BaseView, P : BasePresenter<V> {

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