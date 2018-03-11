package com.playlistappkotlin.data.network.data.track

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.annotations.NonNull

@Entity(tableName = "tracks")
class TrackItem {
    @Expose
    @SerializedName("id")
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Long? = null
    @SerializedName("name")
    @Expose
    @NonNull
    @ColumnInfo(name = "name")
    var name: String? = null
    @SerializedName("duration")
    @Expose
    @NonNull
    @ColumnInfo(name = "duration")
    var duration: String? = null
    @SerializedName("listeners")
    @Expose
    @NonNull
    @ColumnInfo(name = "listeners")
    var listeners: String? = null
    @SerializedName("mbid")
    @Expose
    @NonNull
    @ColumnInfo(name = "b_id")
    var bid: String? = null
    @SerializedName("url")
    @Expose
    @NonNull
    @ColumnInfo(name = "url")
    var url: String? = null
    @SerializedName("streamable")
    @Expose
    @Ignore
    val streamAble: StreamAble? = null
    @SerializedName("artist")
    @Expose
    @Ignore
    val artist: Artist? = null
    @SerializedName("image")
    @Expose
    @Ignore
    val imageList: List<Image>? = null
    @SerializedName("isFavorite")
    @Expose
    @Ignore
    var isFavorite: Boolean = false

    override fun toString(): String {
        return "\n{ " +
                "mName = " + name +
                ", mDuration = " + duration +
                ", mListeners = " + listeners +
                ", mBid = " + bid +
                ", mStreamAble = " + streamAble +
                ", mArtist = " + artist +
                ", mImageList = " + imageList +
                ", mIsFavorite = " + isFavorite +
                " }"
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false

        val that = o as TrackItem?

        return if (name != null) name == that!!.name else that!!.name == null
    }
}
