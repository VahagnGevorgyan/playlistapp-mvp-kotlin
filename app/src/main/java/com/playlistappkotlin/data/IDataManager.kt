package com.playlistappkotlin.data

import com.playlistappkotlin.data.db.IDbHelper
import com.playlistappkotlin.data.network.api.ApiHelper
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.data.network.data.track.TrackResData
import com.playlistappkotlin.data.settings.IAppSettingsHelper
import io.reactivex.Observable

interface IDataManager {

    val apiHelper: ApiHelper

    val settingsHelper: IAppSettingsHelper

    val dbHelper: IDbHelper

    val allTracks: Observable<List<TrackItem>>

    fun doTracksApiCall(country: String,
                        limit: Int?,
                        page: Int?): Observable<TrackResData>

    fun updateTrack(trackItem: TrackItem, remove: Boolean): Observable<Boolean>

    fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean>
}

