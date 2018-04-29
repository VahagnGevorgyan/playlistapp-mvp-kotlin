package com.playlistappkotlin.ui.home.tracks

import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.ui.base.MvpView

interface TracksMvpView : MvpView {

    fun updateTracks(trackItems: List<TrackItem>?)

    fun addTracks(trackItems: List<TrackItem>?)

    fun trackItemUpdated(item: TrackItem, position: Int)
}
