package com.playlistappkotlin.ext

import android.content.Context
import android.graphics.Typeface

class Typefaces {
    companion object {
        private val cache: HashMap<String, Typeface>  = HashMap()

        fun get(c: Context, assetPath: String?): Typeface? {
            synchronized (cache) {
                if (!cache.containsKey(assetPath)) {
                    assetPath?.let {
                        val t: Typeface = Typeface.createFromAsset(c.assets, it)
                        cache[it] = t
                    } ?: run {
                        return null
                    }
                }
                return cache[assetPath];
            }
        }
    }
}
