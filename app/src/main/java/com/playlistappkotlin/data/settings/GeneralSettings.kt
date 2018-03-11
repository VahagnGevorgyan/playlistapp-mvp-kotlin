package com.playlistappkotlin.data.settings

import java.lang.Integer.MIN_VALUE

/**
 * Storing app general information.
 */
class GeneralSettings(prefs: AppPreferences) : BaseSettings(prefs) {

    var splashUrl: String
        get() = prefs.getString(AppPreferences.Settings.SPLASH_URL.key(), "").toString()
        set(splashUrl) {
            prefs.setSetting(AppPreferences.Settings.SPLASH_URL, splashUrl)
        }

    var appVersion: Int
        get() = prefs.getInt(AppPreferences.Settings.PROPERTY_APP_VERSION.key(), MIN_VALUE)
        set(appVersion) {
            prefs.setSetting(AppPreferences.Settings.PROPERTY_APP_VERSION, appVersion)
        }

}