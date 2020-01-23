package com.qifan.lifecyclebehavior

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<out T>(private val presenter: BasePresenter<T>) :
    AppCompatActivity() where T : BaseView {

    override fun onResume() {
        super.onResume()
        presenter.takeView(getView())
    }

    abstract fun getView(): T

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }
}