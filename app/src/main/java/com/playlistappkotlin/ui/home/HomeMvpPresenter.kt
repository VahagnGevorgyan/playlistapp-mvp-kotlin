package com.playlistappkotlin.ui.home

import com.playlistappkotlin.inject.scopes.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
interface HomeMvpPresenter : MvpPresenter {

    fun onNavMenuCreated()
}
