package com.playlistappkotlin.ui.home.favorite

import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.ui.base.MvpView

interface FavoritesMvpView : MvpView {

    fun updateItems(items: List<TrackItem>)

    fun addItems(items: List<TrackItem>)
}
