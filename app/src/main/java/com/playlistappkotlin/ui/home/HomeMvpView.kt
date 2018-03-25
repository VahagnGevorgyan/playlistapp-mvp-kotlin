package com.playlistappkotlin.ui.home

import com.playlistappkotlin.ui.base.MvpView

interface HomeMvpView : MvpView {

    fun showFavoritesFragment()

    fun showAboutFragment()

    fun showSettingsFragment()

    fun showTracksFragment()

    fun lockDrawer()

    fun unlockDrawer()
}

