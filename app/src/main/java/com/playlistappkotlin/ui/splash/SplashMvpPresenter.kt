package com.playlistappkotlin.ui.splash

import com.playlistappkotlin.di.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
public interface SplashMvpPresenter<in V : SplashMvpView> : @JvmSuppressWildcards MvpPresenter<V>
