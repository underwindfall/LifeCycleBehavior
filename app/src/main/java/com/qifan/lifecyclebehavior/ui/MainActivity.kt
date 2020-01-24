package com.qifan.lifecyclebehavior.ui

import android.os.Bundle
import com.qifan.lifecyclebehavior.BaseActivity
import com.qifan.lifecyclebehavior.ErrorGuardBehavior
import com.qifan.lifecyclebehavior.PresenterBehavior
import com.qifan.lifecyclebehavior.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity :
    BaseActivity(),
    MainContract.View,
    PresenterBehavior.Contract<MainContract.View>,
    ErrorGuardBehavior.Contract {

    private val presenterBehavior by lazy { PresenterBehavior(MainContractPresenterImpl(), this) }

    private val errorGuardBehavior by lazy { ErrorGuardBehavior(this) }

    override fun getView(): MainContract.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main_update_button.setOnClickListener { presenterBehavior.presenter.getMessage() }
        // Subscribe observer to lifecycle
        lifecycle.addObserver(presenterBehavior)
        lifecycle.addObserver(errorGuardBehavior)
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }

    override fun getLayout(): Int = R.layout.main_activity
}
