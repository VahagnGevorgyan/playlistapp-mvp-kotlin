package com.playlistappkotlin.ui.splash

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

public class SplashPresenter<V : SplashMvpView> @Inject
public constructor(
        IDataManager: IDataManager,
        ISchedulerProvider: ISchedulerProvider,
        compositeDisposable: CompositeDisposable
) : @JvmSuppressWildcards BasePresenter<V>(IDataManager, ISchedulerProvider, compositeDisposable), @JvmSuppressWildcards SplashMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        mvpView.onSplashAttached(TIMEOUT)
    }

    companion object {

        /**
         * Timeout parameter for delaying before starting next activity.
         */
        private const val TIMEOUT = 2000
    }
}
