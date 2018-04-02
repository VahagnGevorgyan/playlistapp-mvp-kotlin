package com.playlistappkotlin.ui.splash

import android.os.Handler
import com.playlistappkotlin.R
import com.playlistappkotlin.ui.base.ActivityCode
import com.playlistappkotlin.ui.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashMvpView {

    @Inject
    lateinit var mPresenter: SplashMvpPresenter

    private val mTimerHandler = Handler()

    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun initInjector() {
        Timber.d("Injecting \"Splash\" activity")
        activityComponent.inject(this)
    }

    override fun initViews() {
        Timber.d("Initializing view elements")
        mPresenter.onAttach(this@SplashActivity)
    }

    override fun onSplashAttached(timeOut: Int) {
        Timber.d("SplashMvpPresenter is attached")
        mTimerHandler.postDelayed({
            launchActivity(ActivityCode.HOME)
            overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out)
        }, timeOut.toLong())
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }
}
