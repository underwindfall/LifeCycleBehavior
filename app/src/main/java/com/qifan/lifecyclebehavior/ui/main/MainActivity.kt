package com.qifan.lifecyclebehavior.ui.main

import android.content.Intent
import android.os.Bundle
import com.qifan.lifecyclebehavior.BaseActivity
import com.qifan.lifecyclebehavior.ErrorGuardBehavior
import com.qifan.lifecyclebehavior.PresenterBehavior
import com.qifan.lifecyclebehavior.R
import com.qifan.lifecyclebehavior.behavior.Behavior
import com.qifan.lifecyclebehavior.behavior.behaviorFactory
import com.qifan.lifecyclebehavior.ui.second.SecondActivity
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity :
    BaseActivity(),
    MainContract.View,
    PresenterBehavior.Contract<MainContract.View>,
    ErrorGuardBehavior.Contract {

    @Behavior
    private val presenterBehavior by behaviorFactory {
        PresenterBehavior(
            MainContractPresenterImpl(),
            this
        )
    }

    @Behavior
    private val errorGuardBehavior by behaviorFactory { ErrorGuardBehavior(this) }

    override fun getView(): MainContract.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main_update_button.setOnClickListener { presenterBehavior.presenter.getMessage() }
        // Subscribe observer to lifecycle
        lifecycle.addObserver(presenterBehavior)
        lifecycle.addObserver(errorGuardBehavior)
        main_text_view.setOnClickListener { navigateToSecond() }
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }

    override fun navigateToSecond() {
        startActivity(Intent(this, SecondActivity::class.java))
        finish()
    }

    override fun getLayout(): Int = R.layout.main_activity
}
