package com.playlistappkotlin.ui.splash

import com.playlistappkotlin.ui.base.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashMvpView {

    @Inject
    lateinit var mPresenter: SplashMvpPresenter

    override fun attachLayoutRes(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initInjector() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSplashAttached(timeOut: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
