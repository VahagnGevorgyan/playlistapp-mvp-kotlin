package com.playlistappkotlin.ui.home.tracks

import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.ui.base.MvpPresenter

interface TracksMvpPresenter : MvpPresenter {

    fun loadTrackItems()

    fun nextTrackItems()

    fun setFavoriteItem(item: TrackItem, position: Int)
}
