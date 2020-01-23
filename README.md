# Purpose

## Step1

``` kotlin
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

```

## Step2 

```kotlin
abstract class BaseActivity<V, P>(protected val presenter: P) : AppCompatActivity() where V : BaseView, P : BasePresenter<V> {

    override fun onResume() {
        super.onResume()
        presenter.takeView(getView())
    }

    abstract fun getView(): V

    override fun onPause() {
        super.onPause()
        presenter.dropView()
    }
}
```

Magic happens:

```kotlin
class MainActivity :
    BaseActivity<MainContract.View, MainContract.Presenter>(MainContractPresenterImpl()),
    MainContract.View {

    override fun getView(): MainContract.View = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        main_update_button.setOnClickListener { presenter.getMessage() }
    }


    override fun displayMessage(message: String) {
        main_text_view.text = message
    }
}
``` 
