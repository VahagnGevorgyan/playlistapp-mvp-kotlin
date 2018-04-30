package com.playlistappkotlin.ui.home.favorite

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class FavoritesPresenter @Inject constructor(dataManager: IDataManager,
                                             schedulerProvider: ISchedulerProvider,
                                             compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), FavoritesMvpPresenter {

    override fun loadFavoriteItems() {
        if (!isViewAttached) {
            return
        }
        mvpView?.showProgressBar()

        compositeDisposable.add(dataManager
                .allTracks
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ trackItems ->
                    Timber.d("Request all \"Tracks\" from local database was successful $trackItems")

                    mvpView?.hideProgressBar()
                    (mvpView as FavoritesMvpView).updateItems(trackItems)

                }, { throwable ->
                    Timber.e("Request all \"Tracks\" from local database was failed ${throwable.message}")
                    if (!isViewAttached) {
                        return@subscribe
                    }
                    mvpView?.hideProgressBar()
                    mvpView?.showMessage(throwable.message)
                }))
    }

    override fun nextItems() {

    }

    override fun setFavoriteItem(item: TrackItem, position: Int) {

    }
}
