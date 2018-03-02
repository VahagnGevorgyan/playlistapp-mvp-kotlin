@file:Suppress("NAME_SHADOWING")

package com.playlistappkotlin.ext

import android.text.TextUtils
import com.crashlytics.android.Crashlytics


/**
 * Add something to the Crashlytics logfile which on the crash
 * will be uploaded to the Crashlytics as additional details to the crash report.
 */
fun logAtCrashlytics(message: String) {
    if (!TextUtils.isEmpty(message)) {
        Crashlytics.getInstance().core.log(message)
    }
}

/**
 * Force Crashlytics crash upload with this message.
 */
fun forceCrashlyticsCrashWithMessage(message: String, exception: Exception, crashLinePosition: Int) {
    if (!TextUtils.isEmpty(message)) {
        val e = produceException(message, exception, crashLinePosition)

        if (e != null) {
            Crashlytics.getInstance().core.logException(e)
        }
    }
}

/**
 * Produce exception with unique stack trace which begins with the method and line number where Timber.e() was called
 */
private fun produceException(message: String, e: Exception?, crashLinePosition: Int): Exception? {
    var e = e
    if (e == null) {
        e = Exception(message)
    }

    val stackTraceElements = e.stackTrace

    // This place is vulnerable to changes in the stack trace structure. If you will add more methods in the process, or remove some of them,
    // you will fail to get correct top line for the stack trace.
    // Remember to examine stack trace contents in order to know from which place it starts.
    if (stackTraceElements != null && stackTraceElements.size >= crashLinePosition) {
        val remainingLength = stackTraceElements.size - crashLinePosition
        val alteredStackTraceElements = arrayOfNulls<StackTraceElement>(remainingLength)
        System.arraycopy(stackTraceElements, crashLinePosition, alteredStackTraceElements, 0, remainingLength)
        val topLine = alteredStackTraceElements[0]
        if (topLine != null) {
            alteredStackTraceElements[0] = StackTraceElement(topLine.className, topLine.methodName, topLine.fileName, topLine.lineNumber)
        }
        e.stackTrace = alteredStackTraceElements
        return e
    }

    return null
}


