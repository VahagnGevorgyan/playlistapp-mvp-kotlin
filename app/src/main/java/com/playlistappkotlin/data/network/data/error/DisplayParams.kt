package com.playlistappkotlin.data.network.data.error

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Display params of generic error.
 */
class DisplayParams : Serializable {

    @SerializedName("type")
    var type: String? = null
        internal set
    @SerializedName("title")
    var title: String? = null
        internal set
    @SerializedName("body")
    var body: String? = null
        internal set


    override fun toString(): String {
        return "DisplayParams{" +
                "mType='" + type + '\''.toString() +
                ", mTitle='" + title + '\''.toString() +
                ", mBody='" + body + '\''.toString() +
                '}'.toString()
    }

    override fun hashCode(): Int {
        var result = if (type != null) type!!.hashCode() else 0
        result = 31 * result + if (title != null) title!!.hashCode() else 0
        result = 31 * result + if (body != null) body!!.hashCode() else 0
        return result
    }
}
