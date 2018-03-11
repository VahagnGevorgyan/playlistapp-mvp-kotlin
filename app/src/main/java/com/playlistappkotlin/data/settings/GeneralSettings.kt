package com.playlistappkotlin.data.settings

import java.lang.Integer.MIN_VALUE

/**
 * Storing app general information.
 */
class GeneralSettings(prefs: AppPreferences) : BaseSettings(prefs) {

    var splashUrl: String?
        get() = prefs.getString(AppPreferences.Settings.SPLASH_URL.key(), "")
        set(splashUrl) {
            splashUrl?.let { prefs.setSetting(AppPreferences.Settings.SPLASH_URL, it) }
        }

    var appVersion: Int?
        get() = prefs.getInt(AppPreferences.Settings.PROPERTY_APP_VERSION.key(), MIN_VALUE)
        set(appVersion) {
            appVersion?.let { prefs.setSetting(AppPreferences.Settings.PROPERTY_APP_VERSION, it) }
        }
}
