package com.playlistappkotlin.ui.web

import com.playlistappkotlin.inject.scopes.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
interface WebViewMvpPresenter : MvpPresenter {

    fun onPageFinished()
}
