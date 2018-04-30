package com.playlistappkotlin.ui.web

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.playlistappkotlin.R
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_WEB_TITLE
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_WEB_URL
import com.playlistappkotlin.ext.showToast
import com.playlistappkotlin.ui.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class WebViewActivity : BaseActivity(), WebViewMvpView {

    @Inject
    lateinit var presenter: WebViewMvpPresenter

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.textViewTitle)
    lateinit var mTextViewTitle: TextView
    @BindView(R.id.webView)
    lateinit var mWebView: WebView
    @BindView(R.id.progressBarLoad)
    lateinit var mProgressBarLoad: ProgressBar

    private var mWebUrl: String? = null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_web_view
    }

    override fun initInjector() {
        Timber.d("Injecting \"WebView\" activity")
        activityComponent.inject(this)
    }

    override fun initViews() {
        Timber.d("Trying to initialize view elements")
        presenter.onAttach(this@WebViewActivity)
        prepareToolbar()
        setWebView()
    }

    private fun prepareToolbar() {
        setSupportActionBar(mToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return false
    }

    private fun setWebView() {
        Timber.d("Setting web view with url")
        intent.extras?.let {
            mWebUrl = it.getString(EXTRA_WEB_URL)
            mTextViewTitle.text = it.getString(EXTRA_WEB_TITLE)
            loadWebView(mWebUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(url: String?) {
        Timber.d("Loading web view with url $url")
        try {
            mWebView.settings.javaScriptEnabled = true
            mWebView.settings.javaScriptCanOpenWindowsAutomatically = true
            mWebView.webViewClient = object : WebViewClient() {

                override fun onPageFinished(view: WebView, url: String) {
                    Timber.d("Loading web view url finished successful $url")
                    presenter.onPageFinished()
                }

                @RequiresApi(api = Build.VERSION_CODES.M)
                override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                    super.onReceivedError(view, request, error)
                    Timber.d("Loading web view url finished with error ${error.description}")
                    Toast.makeText(this@WebViewActivity, error.description, Toast.LENGTH_SHORT).show()
                }

                override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                    Timber.d("Loading web view url finished with error $description")
                    Toast.makeText(this@WebViewActivity, description, Toast.LENGTH_SHORT).show()
                }
            }
            mWebView.loadUrl(url)
        } catch (e: NullPointerException) {
            Timber.e(e.message)
        }

    }

    @OnClick(R.id.imageButtonCopy)
    fun onCopyClicked() {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(resources.getString(R.string.str_clipboard_message), mWebUrl)
        clipboardManager.primaryClip = clipData
        showToast(this@WebViewActivity, getString(R.string.str_copied))
    }

    override fun hideProgressBar() {
        Timber.d("Hiding progress loading bar")
        if (mProgressBarLoad.visibility == View.VISIBLE) {
            mProgressBarLoad.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}
