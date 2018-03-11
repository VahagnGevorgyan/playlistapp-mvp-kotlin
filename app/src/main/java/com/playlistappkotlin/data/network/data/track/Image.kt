package com.playlistappkotlin.data.network.data.track

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.greenrobot.greendao.annotation.NotNull

@Entity(tableName = "images", foreignKeys = [(ForeignKey(entity = TrackItem::class,
        parentColumns = arrayOf("id"), childColumns = arrayOf("track_id"), onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE))])
class Image {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Expose
    var id = 0
    @SerializedName("trackId")
    @ColumnInfo(name = "track_id")
    @Expose
    @NotNull
    var trackId = 0
    @SerializedName("#text")
    @ColumnInfo(name = "text")
    @Expose
    var text: String? = null
    @SerializedName("size")
    @ColumnInfo(name = "size")
    @Expose
    var size: String? = null
}