package com.playlistappkotlin.data.network.data

import com.google.gson.annotations.SerializedName
import com.playlistappkotlin.data.network.data.error.ApiError
import java.io.Serializable

/**
 * Generic response object.
 */
open class ApiResponse : ApiError(), Serializable {

    @SerializedName("version")
    val version: String? = null
    @SerializedName("msg")
    private val mMsg: String? = null
    @SerializedName("code")
    private val mCode: Int = 0


    val isSuccessful: Boolean
        get() = mErrorCode <= 0

    //        return mMsg.isEmpty(); // TODO: Open after checking
    val isOk: Boolean
        get() = true

    override fun toString(): String {
        return "ApiResponse{" +
                "mVersion='" + version + '\''.toString() +
                ", mCode=" + mCode +
                '}'.toString()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val apiResponse = o as ApiResponse?

        return if (if (mMsg != null) mMsg != apiResponse!!.mMsg else apiResponse!!.mMsg != null) false else mCode == apiResponse.mCode

    }

    override fun hashCode(): Int {
        var result = mMsg?.hashCode() ?: 0
        result = 31 * result + mCode
        return result
    }
}