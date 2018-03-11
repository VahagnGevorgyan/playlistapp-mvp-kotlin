package com.playlistappkotlin.data

import android.content.Context
import com.playlistappkotlin.data.db.IDbHelper
import com.playlistappkotlin.data.network.api.ApiHelper
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.data.network.data.track.TrackResData
import com.playlistappkotlin.data.settings.IAppSettingsHelper
import com.playlistappkotlin.di.ApplicationContext
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
constructor(@param:ApplicationContext private val mContext: Context,
            override val apiHelper: ApiHelper,
            override val settingsHelper: IAppSettingsHelper,
            override val dbHelper: IDbHelper) : IDataManager {


    override val allTracks: Observable<List<TrackItem>>
        get() = dbHelper.allTracks

    override fun doTracksApiCall(country: String,
                                 limit: Int?,
                                 page: Int?): Observable<TrackResData> {
        return apiHelper.doTrackListCall(country, limit, page)
    }

    override fun updateTrack(trackItem: TrackItem, remove: Boolean): Observable<Boolean> {
        return if (remove) {
            dbHelper.deleteTrack(trackItem)
        } else dbHelper.saveTrack(trackItem)
    }

    override fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean> {
        return dbHelper.saveTrackList(trackItems)
    }
}
