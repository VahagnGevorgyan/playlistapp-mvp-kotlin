package com.playlistappkotlin.ext.logging

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import com.playlistappkotlin.ext.TIMBER_CRASH_LINE_POSITION
import com.playlistappkotlin.ext.forceCrashlyticsCrashWithMessage
import com.playlistappkotlin.ext.logAtCrashlytics
import timber.log.Timber

/**
 *  Logging tree for development (debugging) tasks.
 */
class DevelopmentTree: Timber.DebugTree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        when (priority) {
            Log.DEBUG, Log.INFO, Log.WARN -> logAtCrashlytics(message)
            Log.ERROR -> {
                val crashException = Exception(message)
                val crashReportingTask = @SuppressLint("StaticFieldLeak")
                object : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void): Void? {
                        forceCrashlyticsCrashWithMessage(message, crashException, TIMBER_CRASH_LINE_POSITION)
                        return null
                    }
                }
                crashReportingTask.execute()
            }
            else -> {
            }
        }
        super.log(priority, tag, message, t)
    }
}