package com.playlistappkotlin.ui.home.about

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AboutPresenter@Inject constructor(dataManager: IDataManager,
                                        schedulerProvider: ISchedulerProvider,
                                        compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), AboutMvpPresenter

