package com.playlistappkotlin.data.db

import com.playlistappkotlin.data.db.model.TrackDao
import com.playlistappkotlin.data.network.data.track.TrackItem
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbHelper @Inject
constructor(private val mTrackDao: TrackDao) : IDbHelper {

    override val allTracks: Observable<List<TrackItem>>
        get() = Observable.fromCallable({ mTrackDao.loadAllTracks() })

    override fun saveTrack(trackItem: TrackItem): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.insertTrack(trackItem)
            true
        }
    }

    override fun deleteTrack(track: TrackItem): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.deleteTrack(track)
            true
        }
    }

    override fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.insertAll(trackItems)
            true
        }
    }

}