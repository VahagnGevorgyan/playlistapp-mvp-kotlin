package com.playlistappkotlin.ui.home

import com.playlistappkotlin.di.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
public interface HomeMvpPresenter<in V : HomeMvpView> : MvpPresenter<V> {

    fun onNavMenuCreated()
}
