package com.playlistappkotlin.data.settings

import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Settings class for working with shared preferences.
 */
@Singleton
class AppSettingsHelper @Inject
constructor(prefs: AppPreferences) : IAppSettingsHelper {

    private var components: HashMap<Class<*>, BaseSettings>? = null

    init {
        initializeComponents(prefs)
    }

    override fun general(): GeneralSettings {
        return components!![GeneralSettings::class.java] as GeneralSettings
    }

    override fun search(): SearchSettings {
        return components!![SearchSettings::class.java] as SearchSettings
    }

    private fun initializeComponents(prefs: AppPreferences) {
        components = HashMap()
        components!![GeneralSettings::class.java] = GeneralSettings(prefs)
        components!![SearchSettings::class.java] = SearchSettings(prefs)
    }
}