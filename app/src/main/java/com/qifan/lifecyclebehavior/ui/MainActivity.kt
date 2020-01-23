package com.qifan.lifecyclebehavior.ui

import android.os.Bundle
import com.qifan.lifecyclebehavior.BaseActivity
import com.qifan.lifecyclebehavior.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity :
    BaseActivity<MainContract.View, MainContract.Presenter>(MainContractPresenterImpl()),
    MainContract.View {

    override fun getView(): MainContract.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        main_update_button.setOnClickListener { presenter.getMessage() }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }
}