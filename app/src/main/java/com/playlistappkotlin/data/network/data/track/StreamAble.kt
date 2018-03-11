package com.playlistappkotlin.data.network.data.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StreamAble {

    @SerializedName("#text")
    @Expose
    val text: String? = null
    @SerializedName("fulltrack")
    @Expose
    val fullTrack: String? = null
}
