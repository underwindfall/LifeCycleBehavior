# Purpose
**Don't repeat yourself** (**DRY**, or sometimes **do not repeat yourself**) is a principle of software development aimed at reducing repetition of software patterns, replacing it with abstractions or using data normalization to avoid redundancy.

## Step1
**Don't repeat yourself** (**DRY**, or sometimes **do not repeat yourself**) is a principle of software development aimed at reducing repetition of software patterns, replacing it with abstractions or using data normalization to avoid redundancy.

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

## Step6 
### split base activity
We try to split the responsibility to each abstract activity, so in a way we kind of solve the problem.

Here we are, those who need presenter must be extended from `PresenterActivity`

```kotlin
abstract class PresenterActivity<V, P>(protected val presenter: P) :
    ErrorGuardActivity() where V : BaseView, P : BasePresenter<V> {

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

```kotlin
abstract class ErrorGuardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ErrorGuardEvent.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        ErrorGuardEvent.unRegister()
    }
}
```

```kotlin
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
    }

    @LayoutRes
    abstract fun getLayout(): Int
}
```

Is there another way to do that ? However we have another issue which is not everyActivity want 
to have feature `ErrorGuard`, it's basically an optional feature we have.

What a pity !!! :(

It's kind of loss the advantage of `BaseActivity`

# Step6
Why should we use **Composition Pattern** to delegate feature to each SubActivity ?

```kotlin
class PresenterBehavior<V, P>(
    val presenter: P,
    private val contract: Contract<V>
) where V : BaseView, P : BasePresenter<V> {
    fun onResume() {
        presenter.takeView(contract.getView())
    }

    fun onPause() {
        presenter.dropView()
    }

    interface Contract<V> {
        fun getView(): V
    
```

```kotlin
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
        errorGuardBehavior.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenterBehavior.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenterBehavior.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        errorGuardBehavior.onDestroy()
    }

    override fun displayMessage(message: String) {
        main_text_view.text = message
    }

    override fun getLayout(): Int = R.layout.main_activity
}
```