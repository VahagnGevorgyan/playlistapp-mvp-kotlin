package com.playlistappkotlin.ui.web

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WebViewPresenter<V : WebViewMvpView> @Inject
public constructor(
        IDataManager: IDataManager,
        ISchedulerProvider: ISchedulerProvider,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V>(IDataManager, ISchedulerProvider, compositeDisposable), WebViewMvpPresenter<V> {

    override fun onPageFinished() {
        if (!isViewAttached) {
            return
        }
        mvpView?.hideProgressBar()
    }
}
