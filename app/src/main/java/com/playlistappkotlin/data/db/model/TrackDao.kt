package com.playlistappkotlin.data.db.model

import android.arch.persistence.room.*
import com.playlistappkotlin.data.network.data.track.TrackItem

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks ORDER BY id ASC")
    fun loadAllTracks(): List<TrackItem>

    @Query("SELECT * FROM tracks where Id = :trackId")
    fun loadTrack(trackId: Int): TrackItem

    @Insert
    fun insertAll(trackItems: List<TrackItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrack(trackItem: TrackItem): Long?

    @Delete
    fun deleteTrack(trackItem: TrackItem): Int

    @Query("DELETE FROM tracks")
    fun deleteAllTracks(): Int
}
