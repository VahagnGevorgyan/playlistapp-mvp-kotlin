package com.playlistappkotlin.ui.splash

import com.playlistappkotlin.ui.base.MvpView

interface SplashMvpView: MvpView {

    fun onSplashAttached(timeOut: Int)
}
