package com.playlistappkotlin.data.network.data.track

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Attributes {
    @SerializedName("country")
    @Expose
    val country: String? = null
    @SerializedName("page")
    @Expose
    val page: String? = null
    @SerializedName("perPage")
    @Expose
    val perPage: String? = null
    @SerializedName("totalPages")
    @Expose
    val totalPages: String? = null
    @SerializedName("total")
    @Expose
    val total: String? = null
}
