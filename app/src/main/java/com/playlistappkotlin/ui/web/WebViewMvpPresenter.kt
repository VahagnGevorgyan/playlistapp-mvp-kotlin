package com.playlistappkotlin.ui.web

import com.playlistappkotlin.di.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
interface WebViewMvpPresenter<in V : WebViewMvpView> : MvpPresenter<V> {

    fun onPageFinished()
}

