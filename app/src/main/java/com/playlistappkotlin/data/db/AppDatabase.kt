package com.playlistappkotlin.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.playlistappkotlin.data.db.model.TrackDao
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.ext.Constants.Companion.DATABASE_VERSION

@Database(entities = [(TrackItem::class)], version = DATABASE_VERSION, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackDao(): TrackDao
}
