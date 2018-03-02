package com.playlistappkotlin.ext

import android.content.Context
import android.graphics.Typeface
import timber.log.Timber
import java.util.*

private val cache = HashMap<String, Typeface>()

fun get(c: Context, assetPath: String): Typeface? {
    synchronized(cache) {
        if (!cache.containsKey(assetPath)) {
            try {
                val t = Typeface.createFromAsset(c.assets,
                        assetPath)
                cache[assetPath] = t
            } catch (e: Exception) {
                Timber.e("Could not get typeface '" + assetPath
                        + "' because " + e.message)
                return null
            }

        }
        return cache[assetPath]
    }
}