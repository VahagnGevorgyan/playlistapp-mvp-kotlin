package com.playlistappkotlin.ui.splash

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import com.playlistappkotlin.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplashPresenter @Inject constructor(dataManager: IDataManager,
                                          schedulerProvider: ISchedulerProvider,
                                          compositeDisposable: CompositeDisposable
) : BasePresenter(dataManager, schedulerProvider, compositeDisposable), SplashMvpPresenter {

    override fun onAttach(mvpView: MvpView) {
        super.onAttach(mvpView)

        (mvpView as SplashMvpView).onSplashAttached(TIMEOUT)
    }

    companion object {

        /**
         * Timeout parameter for delaying before starting next activity.
         */
        private const val TIMEOUT = 2000
    }
}
