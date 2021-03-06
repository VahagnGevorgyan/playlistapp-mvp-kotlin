package com.playlistappkotlin.data.network.data.error

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Generic error for 'KO' response.
 */
open class ApiError : Exception() {

    @SerializedName("message")
    @Expose
    protected var mMessage: String? = null
    @SerializedName("status")
    @Expose
    private val mStatus: String? = null
    @SerializedName("error_code")
    @Expose
    protected var mErrorCode: Int = 0

    override fun toString(): String {
        return "Error{" +
                "mMessage='" + mMessage + '\''.toString() +
                ", mStatus='" + mStatus + '\''.toString() +
                ", mErrorCode='" + mErrorCode + '\''.toString() +
                '}'.toString()
    }

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val error = other as ApiError?

        return if (mMessage != null) mMessage == error!!.mMessage else error!!.mMessage == null
    }

    override fun hashCode(): Int {
        return if (mMessage != null) mMessage!!.hashCode() else 0
    }
}