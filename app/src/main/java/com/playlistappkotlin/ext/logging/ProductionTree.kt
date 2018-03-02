package com.playlistappkotlin.ext.logging

import android.util.Log
import com.playlistappkotlin.ext.logAtCrashlytics
import timber.log.Timber

/**
 *  Logging tree for production (release) version.
 */
class ProductionTree: Timber.DebugTree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        when (priority) {
            Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR -> logAtCrashlytics(message)
            else -> {
            }
        }
        super.log(priority, tag, message, t)
    }
}