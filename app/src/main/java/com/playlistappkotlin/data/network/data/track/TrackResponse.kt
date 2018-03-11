package com.playlistappkotlin.data.network.data.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.playlistappkotlin.data.network.data.ApiResponse

class TrackResponse : ApiResponse() {

    @SerializedName("tracks")
    @Expose
    var data: TrackResData? = null
}
