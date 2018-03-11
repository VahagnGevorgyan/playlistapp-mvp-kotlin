package com.playlistappkotlin.data.network.session

import com.playlistappkotlin.ext.Constants.Companion.ENDPOINT_API_BASE
import com.playlistappkotlin.ext.Constants.Companion.ENDPOINT_TRACK_LIST
import com.playlistappkotlin.ext.Constants.Companion.FORMAT_WEB_SERVICE
import timber.log.Timber
import java.net.MalformedURLException
import java.net.URL

/**
 * Application Session.
 * Settings, cached data, etc.
 * When shutting down the app, this data will disappear without a trace. No persistence.
 * Class is able to reconstruct itself, if Android nullifies Singletons.
 */
class Session (base: String, apiKey: String) {

    val trackListServiceURL: String

    private var mIsLoggedIn: Boolean = false
    private val mLock = Any()


    /**
     * Tells if the User is logged in.
     */
    var isLoggedIn: Boolean
        get() = synchronized(mLock) {
            Timber.d("Is the User logged in? " + mIsLoggedIn)
            return mIsLoggedIn
        }
        set(loggedIn) = synchronized(mLock) {
            Timber.d("Setting User logged in status to " + loggedIn)
            mIsLoggedIn = loggedIn
        }


    init {
        Timber.d("Creating App Session")
        trackListServiceURL = constructFullUrl(constructBaseUrl(base, apiKey, ENDPOINT_API_BASE), ENDPOINT_TRACK_LIST)
    }

    /**
     * Creates a base url for further use.
     */
    fun constructBaseUrl(base: String, apiKey: String, endpoint: String): String {
        try {
            // Endpoint is a valid url, we should not modify it.
            // TODO Better way to do this?
            URL(endpoint)
            return endpoint
        } catch (e: MalformedURLException) {
            // Nothing to do here, move along.
        }

        val fullUrl = base + endpoint
        return fullUrl
                .replace("//".toRegex(), "/")
                .replace(":/", "://")
                .replace("{api_key}", apiKey)
                .replace("{format}", FORMAT_WEB_SERVICE)
    }

    /**
     * Creates a full url for further use.
     */
    fun constructFullUrl(base: String, endpoint: String): String {
        try {
            // Endpoint is a valid url, we should not modify it.
            // TODO Better way to do this?
            URL(endpoint)
            return endpoint
        } catch (e: MalformedURLException) {
            // Nothing to do here, move along.
        }

        val fullUrl = base + endpoint
        return fullUrl
                .replace("//".toRegex(), "/")
                .replace(":/", "://")
    }
}
