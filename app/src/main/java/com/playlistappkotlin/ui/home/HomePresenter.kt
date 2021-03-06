package com.playlistappkotlin.ui.home

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter @Inject constructor(dataManager: IDataManager,
                                        schedulerProvider: ISchedulerProvider,
                                        compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), HomeMvpPresenter {

    override fun onNavMenuCreated() {
        if (!isViewAttached) {
            return
        }
        (mvpView as HomeMvpView).showTracksFragment()
    }
}
