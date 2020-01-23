package com.qifan.lifecyclebehavior

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<V, P>(protected val presenter: P) :
    AppCompatActivity() where V : BaseView, P : BasePresenter<V> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(getView())
    }

    abstract fun getView(): V

    @LayoutRes
    abstract fun getLayout(): Int

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }
}