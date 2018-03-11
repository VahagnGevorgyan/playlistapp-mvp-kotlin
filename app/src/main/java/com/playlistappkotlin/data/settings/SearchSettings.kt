package com.playlistappkotlin.data.settings

import com.playlistappkotlin.ext.Constants.Companion.DEFAULT_TRACK_COUNTRY
import com.playlistappkotlin.ext.Constants.Companion.DEFAULT_TRACK_LIMIT_COUNT

/**
 * Storing search filters.
 */
class SearchSettings(prefs: AppPreferences) : BaseSettings(prefs) {

    var limitCount: Int
        get() =
            prefs.getInt(AppPreferences.Settings.TRACK_LIMIT_COUNT.key(), DEFAULT_TRACK_LIMIT_COUNT)
        set(limitCount) {
            prefs.setSetting(AppPreferences.Settings.TRACK_LIMIT_COUNT, limitCount)
        }

    var country: String?
        get() = prefs.getString(AppPreferences.Settings.TRACK_COUNTRY.key(), DEFAULT_TRACK_COUNTRY)
        set(country) {
            country?.let { prefs.setSetting(AppPreferences.Settings.TRACK_COUNTRY, it) }
        }
}
