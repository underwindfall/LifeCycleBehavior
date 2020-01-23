package com.qifan.lifecyclebehavior

interface BasePresenter<T> where T : BaseView {
    var view: T?
    fun takeView(v: T)
    fun dropView()
}