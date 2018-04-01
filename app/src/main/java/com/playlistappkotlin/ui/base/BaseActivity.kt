package com.playlistappkotlin.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.playlistappkotlin.App
import com.playlistappkotlin.R
import com.playlistappkotlin.ext.getNavigationBarSize
import com.playlistappkotlin.ext.getStatusBarHeight
import com.playlistappkotlin.ext.network.NetworkStateHelper
import com.playlistappkotlin.ext.network.NetworkStateManager
import com.playlistappkotlin.ext.setWindowUiVisibility
import com.playlistappkotlin.ext.showLoadingDialog
import com.playlistappkotlin.inject.components.ActivityComponent
import com.playlistappkotlin.inject.components.DaggerActivityComponent
import com.playlistappkotlin.inject.modules.ActivityModule
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

abstract class BaseActivity : EventBusActivity(), MvpView, BaseFragment.Callback {

    @Inject
    lateinit var mNetworkStateHelper: NetworkStateHelper

    /**
     * Layout container for using some cases.
     */
    @BindView(R.id.layout_container)
    internal var mContainer: View? = null

    /**
     * Progress bar for using local loading.
     */
    @BindView(R.id.progressBar)
    internal var mProgressBar: ProgressBar? = null

    /**
     * ButterKnife binder.
     */
    private var mUnBinder: Unbinder? = null

    private var mProgressDialog: ProgressDialog? = null

    internal val activityComponent: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(App.mAppComponent)
                .build()
    }

    override val isNetworkConnected: Boolean
        get() = NetworkStateManager.isConnected(applicationContext)

    /**
     * Bind layout file.
     *
     * @return layout file id
     */
    @LayoutRes
    protected abstract fun attachLayoutRes(): Int

    /**
     * Inject Dagger components.
     */
    protected abstract fun initInjector()

    /**
     * Initializing layout views.
     */
    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareWindow()
        setContentView(attachLayoutRes())

        try {
            ActivityComponent::class.java.getDeclaredMethod("inject", this::class.java).invoke(activityComponent, this)
        } catch(e: NoSuchMethodException) {
            throw Exception("You forgot to add \"fun inject(activity: ${this::class.java.simpleName})\" in ActivityComponent")
        }

        mUnBinder = ButterKnife.bind(this)
        initInjector()
        initViews()
        prepareLayout()
    }

    protected override fun onResume() {
        super.onResume()

        initializeNetworkStateManager()
    }

    private fun prepareWindow() {
        Timber.d("Preparing app window adding flags")
        setWindowUiVisibility(window)
    }

    protected open fun prepareLayout() {
        Timber.d("Preparing activity layout and set sizes, if software buttons exists on screen")
        mContainer?.let {
            it.setPadding(it.paddingLeft, it.paddingTop + getStatusBarHeight(this, false), it.paddingRight,
                    getNavigationBarSize(this).y + it.paddingBottom)
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun showProgressBar() {
        hideProgressBar()
        mProgressBar?.let {
            it.visibility = VISIBLE
        }
    }

    override fun hideProgressBar() {
        mProgressBar?.let {
            it.visibility = View.GONE
        }
    }

    override fun showLoading() {
        hideLoading()
        mProgressDialog = showLoadingDialog(this)
    }

    override fun hideLoading() {
        mProgressDialog?.let {
            if (it.isShowing) {
                it.cancel()
            }
        }
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val sbView = snackBar.view
        val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackBar.show()
    }

    private fun initializeNetworkStateManager() {
        Timber.d("Tyring to initialize \"NetworkStateManager\"")
        mNetworkStateHelper.initializeNetworkStatusListener()
    }

    override fun onError(message: String?) {
        message?.let {
            showSnackBar(it)
        } ?: run {
            showSnackBar(getString(R.string.some_error))
        }
    }

    override fun onError(@StringRes resId: Int) {
        onError(getString(resId))
    }

    override fun showMessage(message: String?) {
        message?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

    override fun onFragmentAttached() {
        Timber.d("Fragment attached")
    }

    override fun onFragmentDetached(tag: String) {
        Timber.d("Fragment detached")
    }

    override fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected override fun onDestroy() {
        mUnBinder?.unbind()
        super.onDestroy()
    }

    @JvmOverloads
    protected fun launchActivity(code: ActivityCode, flags: Int? = null) {
        when (code) {
            ActivityCode.HOME -> launchMainActivity()
            else -> launchMainActivity()
        }
    }

    private fun launchMainActivity() {
//        launchActivity(HomeActivity::class.java, Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        finish()
    }

    private fun launchActivity(clazz: Class<out Activity>) {
        startActivity(Intent(this, clazz))
    }

    private fun launchActivity(clazz: Class<out Activity>, flags: Int) {
        val intent = Intent(this, clazz)
        intent.addFlags(flags)
        startActivity(intent)
    }
}
