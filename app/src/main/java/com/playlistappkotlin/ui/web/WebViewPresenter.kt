package com.playlistappkotlin.ui.web

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class WebViewPresenter @Inject constructor(dataManager: IDataManager,
                                           schedulerProvider: ISchedulerProvider,
                                           compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), WebViewMvpPresenter {

    override fun onPageFinished() {
        if (!isViewAttached) {
            return
        }
        mvpView?.hideProgressBar()
    }
}
