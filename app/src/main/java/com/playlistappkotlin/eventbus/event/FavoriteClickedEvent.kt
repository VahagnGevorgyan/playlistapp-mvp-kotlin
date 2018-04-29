package com.playlistappkotlin.eventbus.event

import com.playlistappkotlin.data.network.data.track.TrackItem

class FavoriteClickedEvent(val item: TrackItem, val position: Int)