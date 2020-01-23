# Purpose
**Don't repeat yourself** (**DRY**, or sometimes **do not repeat yourself**) is a principle of software development aimed at reducing repetition of software patterns,[1] replacing it with abstractions or using data normalization to avoid redundancy.

## Step1
**Don't repeat yourself** (**DRY**, or sometimes **do not repeat yourself**) is a principle of software development aimed at reducing repetition of software patterns,[1] replacing it with abstractions or using data normalization to avoid redundancy.

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

## Step3

It's possible to add more potentially useful functions in `BaseActivity`

```kotlin
  @LayoutRes
  abstract fun getLayout(): Int
```

## Step4
However is it a right way to do this ? Should we put more code just not for repeat yourself ?

We are trying to add more complicated code such as **TrackingEvent,ErrorGuardEvent** in `BaseActivity` 

```kotlin
abstract class BaseActivity<V, P>(protected val presenter: P) :
    AppCompatActivity() where V : BaseView, P : BasePresenter<V> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        ErrorGuardEvent.register()
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

    override fun onDestroy() {
        super.onDestroy()
        ErrorGuardEvent.unRegister()
    }
}
``` 
We definitely don't want put everything into `BaseActivity`. We argue to find a solution to it.

