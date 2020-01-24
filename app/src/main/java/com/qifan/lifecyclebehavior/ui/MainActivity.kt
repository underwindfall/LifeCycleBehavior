package com.qifan.lifecyclebehavior.ui

import android.os.Bundle
import com.qifan.lifecyclebehavior.PresenterActivity
import com.qifan.lifecyclebehavior.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity :
    PresenterActivity<MainContract.View, MainContract.Presenter>(MainContractPresenterImpl()),
    MainContract.View {

    override fun getView(): MainContract.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main_update_button.setOnClickListener { presenter.getMessage() }
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }

    override fun getLayout(): Int = R.layout.main_activity
}
