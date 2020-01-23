package com.qifan.lifecyclebehavior.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qifan.lifecyclebehavior.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter: MainContract.Presenter = MainContractPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.getMessage()
    }

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }
}
