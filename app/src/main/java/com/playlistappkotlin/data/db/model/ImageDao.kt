package com.playlistappkotlin.data.db.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.playlistappkotlin.data.network.data.track.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image): Int

    @Query("SELECT * FROM images")
    fun loadAllImages(): List<Image>
}
