package com.qifan.lifecyclebehavior.ui.main

class MainContractPresenterImpl : MainContract.Presenter {
    override var view: MainContract.View? = null

    override fun takeView(v: MainContract.View) {
        this.view = v
    }

    override fun dropView() {
        this.view = null
    }

    override fun getMessage() {
        view?.displayMessage("Hello World")
    }

}