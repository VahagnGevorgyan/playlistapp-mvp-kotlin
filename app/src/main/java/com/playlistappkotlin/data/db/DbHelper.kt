package com.playlistappkotlin.data.db

import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DbHelper @Inject
constructor(private val mTrackDao: TrackDao) : IDbHelper {

    val allTracks: Observable<List<TrackItem>>
        get() = Observable.fromCallable({ mTrackDao.loadAllTracks() })

    fun saveTrack(track: TrackItem): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.insertTrack(track)
            true
        }
    }

    fun deleteTrack(track: TrackItem): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.deleteTrack(track)
            true
        }
    }

    fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean> {
        return Observable.fromCallable {
            mTrackDao.insertAll(trackItems)
            true
        }
    }

}