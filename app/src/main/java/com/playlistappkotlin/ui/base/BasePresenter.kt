package com.playlistappkotlin.ui.base

import com.playlistappkotlin.R
import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.network.data.error.ApiError
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

public open class BasePresenter<V : MvpView> @Inject
public constructor(
        val dataManager: IDataManager,
        val schedulerProvider: ISchedulerProvider,
        private val mCompositeDisposable: CompositeDisposable
) : @JvmSuppressWildcards MvpPresenter<V> {

    var mvpView: V? = null
        private set

    val isViewAttached: Boolean
        get() = mvpView != null

    val compositeDisposable: CompositeDisposable
        get() = if (mCompositeDisposable.isDisposed) CompositeDisposable() else mCompositeDisposable

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        mCompositeDisposable.dispose()
        mvpView = null
    }

    override fun handleApiError(error: ApiError?) {
        if (error == null) {
            mvpView?.onError(R.string.some_error)
            return
        }
        error.message?.let { mvpView?.onError(it) }
    }
}
