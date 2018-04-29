package com.playlistappkotlin.ui.home.tracks

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.network.data.error.ApiError
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.data.network.data.track.TrackResData
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class TracksPresenter @Inject constructor(dataManager: IDataManager,
                                          schedulerProvider: ISchedulerProvider,
                                          compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), TracksMvpPresenter {

    private var mPageId: Int = 0

    override fun loadTrackItems() {
        if (!isViewAttached) {
            return
        }
        mvpView?.showProgressBar()

        mPageId = 1
        callTracksApi(mPageId, { trackResData ->
            Timber.d("Api request \"doTracksApiCall\" was successful ${trackResData.trackItems}")

            if (!isViewAttached) {
                return@callTracksApi
            }
            mvpView?.hideProgressBar()
            (mvpView as TracksMvpView).updateTracks(trackResData.trackItems)
        })
    }

    override fun nextTrackItems() {
        if (!isViewAttached) {
            return
        }
        mvpView?.showProgressBar()

        mPageId++

        callTracksApi(mPageId, { trackResData ->
            Timber.d("Api request \"doTracksApiCall\" was successful $trackResData")

            if (!isViewAttached) {
                return@callTracksApi
            }
            mvpView?.hideProgressBar()
            (mvpView as TracksMvpView).addTracks(trackResData.trackItems)
        })
    }

    override fun setFavoriteItem(item: TrackItem, position: Int) {
        if (!isViewAttached) {
            return
        }
        mvpView?.showProgressBar()

        compositeDisposable.add(dataManager
                .updateTrack(item, item.isFavorite)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ aBoolean ->
                    Timber.d("Updating track item was successful $aBoolean")

                    mvpView?.hideProgressBar()
                    (mvpView as TracksMvpView).trackItemUpdated(item, position)
                }, { throwable ->
                    Timber.e("Updating track item was failed ${throwable.message}")

                    if (!isViewAttached) {
                        return@subscribe
                    }
                    mvpView?.hideProgressBar()
                    mvpView?.showMessage(throwable.message)
                }))
    }

    /**
     * Requesting to Tracks Api web service.
     */
    private fun callTracksApi(pageId: Int, listener: (TrackResData) -> Unit) {
        Timber.d("Calling Tracks web service")

        Timber.d("page $pageId")
        Timber.d("country ${dataManager.settingsHelper.search().country}")
        Timber.d("limit ${dataManager.settingsHelper.search().limitCount}")

        dataManager.settingsHelper.search().country?.let {
            compositeDisposable.add(dataManager
                    .doTracksApiCall(
                            it,
                            dataManager.settingsHelper.search().limitCount,
                            pageId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                            listener,
                            { throwable ->
                                Timber.e("Api request \"doTracksApiCall\" was failed ${throwable.message}")

                                if (!isViewAttached) {
                                    return@subscribe
                                }

                                mvpView?.hideProgressBar()
                                if (throwable is ApiError) {
                                    handleApiError(throwable)
                                }
                            }))
        }
    }
}
