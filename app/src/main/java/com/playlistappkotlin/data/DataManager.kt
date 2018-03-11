package com.playlistappkotlin.data

import android.content.Context
import com.playlistappkotlin.data.db.IDbHelper
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.data.network.data.track.TrackResData
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.data.network.api.ApiHelper
import com.playlistappkotlin.data.settings.IAppSettingsHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(@param:ApplicationContext private val mContext: Context,
            val apiHelper: ApiHelper,
            val settingsHelper: IAppSettingsHelper,
            val dbHelper: IDbHelper) : IDataManager {


    val allTracks: Observable<List<TrackItem>>
        get() = dbHelper.allTracks

    fun doTracksApiCall(country: String,
                        limit: Int?,
                        page: Int?): Observable<TrackResData> {
        return apiHelper.doTrackListCall(country, limit, page)
    }

    fun updateTrack(trackItem: TrackItem, remove: Boolean): Observable<Boolean> {
        return if (remove) {
            dbHelper.deleteTrack(trackItem)
        } else dbHelper.saveTrack(trackItem)
    }

    fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean> {
        return dbHelper.saveTrackList(trackItems)
    }
}
