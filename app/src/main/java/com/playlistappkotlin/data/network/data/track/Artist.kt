package com.playlistappkotlin.data.network.data.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Artist {

    @SerializedName("name")
    @Expose
    val name: String? = null
    @SerializedName("mbid")
    @Expose
    val bid: String? = null
    @SerializedName("url")
    @Expose
    val url: String? = null
}
