package com.qifan.lifecyclebehavior.ui

import com.qifan.lifecyclebehavior.BasePresenter
import com.qifan.lifecyclebehavior.BaseView

interface MainContract {
    interface Presenter : BasePresenter<View> {
        fun getMessage()
    }

    interface View : BaseView {
        fun displayMessage(message: String)
    }
}