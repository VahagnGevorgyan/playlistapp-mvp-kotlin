package com.playlistappkotlin.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.Unbinder
import com.playlistappkotlin.R
import com.playlistappkotlin.di.component.ActivityComponent

abstract class BaseFragment : EventBusFragment(), MvpView {

    var baseActivity: BaseActivity? = null
        private set
    private var mUnBinder: Unbinder? = null
    private var mProgressDialog: ProgressDialog? = null

    /**
     * Progress bar for using local loading.
     */
    @BindView(R.id.progressBar)
    internal var mProgressBar: ProgressBar? = null

    /**
     * @return id of fragment
     */
    @get:LayoutRes
    protected abstract val rootViewId: Int

    override val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    val activityComponent: ActivityComponent?
        get() = if (baseActivity != null) {
            baseActivity!!.activityComponent
        } else null

    /**
     * Initialize injector
     */
    protected abstract fun initInjector(view: View)

    /**
     * Initialize your views here
     *
     * @param rootView parent view of the fragment
     */
    protected abstract fun prepareView(rootView: View)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(rootViewId, container, false)
        initInjector(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun showProgressBar() {
        hideProgressBar()
        if (mProgressBar != null) {
            mProgressBar!!.visibility = VISIBLE
        }
    }

    override fun hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar!!.visibility = View.GONE
        }
    }

    override fun showLoading() {
        hideLoading()
//        mProgressDialog = CommonUtils.showLoadingDialog(this.context)
    }

    override fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    override fun onError(message: String?) {
        if (baseActivity != null) {
            baseActivity!!.onError(message)
        }
    }

    override fun onError(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.onError(resId)
        }
    }

    override fun showMessage(message: String?) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(message)
        }
    }

    override fun showMessage(@StringRes resId: Int) {
        if (baseActivity != null) {
            baseActivity!!.showMessage(resId)
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }


    fun setUnBinder(unBinder: Unbinder) {
        mUnBinder = unBinder
    }

    override fun onDestroy() {
        if (mUnBinder != null) {
            mUnBinder!!.unbind()
        }
        super.onDestroy()
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
