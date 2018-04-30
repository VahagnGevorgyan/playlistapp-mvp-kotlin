package com.playlistappkotlin.ui.home.favorite

import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.inject.scopes.PerActivity
import com.playlistappkotlin.ui.base.MvpPresenter

@PerActivity
interface FavoritesMvpPresenter : MvpPresenter {

    fun loadFavoriteItems()

    fun nextItems()

    fun setFavoriteItem(item: TrackItem, position: Int)
}
