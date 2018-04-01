package com.playlistappkotlin.ui.base

import com.playlistappkotlin.data.network.data.error.ApiError

interface MvpPresenter {

    fun onAttach(mvpView: MvpView)

    fun onDetach()

    fun handleApiError(error: ApiError?)
}
