package com.playlistappkotlin.ui.base

import com.playlistappkotlin.data.network.data.error.ApiError

interface MvpPresenter<in V : MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

    fun handleApiError(error: ApiError?)
}