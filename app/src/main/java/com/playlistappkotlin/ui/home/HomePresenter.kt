package com.playlistappkotlin.ui.home

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class HomePresenter<V : HomeMvpView> @Inject
constructor(
        IDataManager: IDataManager,
        ISchedulerProvider: ISchedulerProvider,
        compositeDisposable: CompositeDisposable
) : BasePresenter<V>(IDataManager, ISchedulerProvider, compositeDisposable), HomeMvpPresenter<V> {

    override fun onNavMenuCreated() {
        if (!isViewAttached) {
            return
        }
        mvpView?.showTracksFragment()
    }
}
