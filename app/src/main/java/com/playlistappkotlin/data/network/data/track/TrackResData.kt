package com.playlistappkotlin.data.network.data.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TrackResData {

    @SerializedName("track")
    @Expose
    val trackItems: List<TrackItem>? = null

    @SerializedName("@attr")
    @Expose
    val data: Attributes? = null
}