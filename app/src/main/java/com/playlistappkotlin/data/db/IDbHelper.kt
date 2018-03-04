package com.playlistappkotlin.data.db

import io.reactivex.Observable

interface IDbHelper {

    val allTracks: Observable<List<TrackItem>>

    fun saveTrack(trackItem: TrackItem): Observable<Boolean>

    fun deleteTrack(track: TrackItem): Observable<Boolean>

    fun saveTrackList(trackItems: List<TrackItem>): Observable<Boolean>

}
