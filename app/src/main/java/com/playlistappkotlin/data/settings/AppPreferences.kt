package com.playlistappkotlin.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.di.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Various methods for storing data in shared preferences.
 */
@Singleton
class AppPreferences @Inject
constructor(@ApplicationContext context: Context,
            @PreferenceInfo prefFileName: String) : SharedPreferences {

    private val sPreferences: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    /**
     * Settings keys.
     */
    enum class Settings(private val key: String, private val type: Class<*>) {
        LOGGED_IN_MODE("LOGGED_IN_MODE", Int::class.java),

        IS_FIRST_LAUNCH("IS_FIRST_LAUNCH", Boolean::class.java),
        SPLASH_URL("SPLASH_URL", String::class.java),
        PROPERTY_APP_VERSION("PROPERTY_APP_VERSION", Int::class.java),
        TRACK_LIMIT_COUNT("TRACK_LIMIT_COUNT", Int::class.java),
        TRACK_COUNTRY("TRACK_COUNTRY", String::class.java);

        fun key(): String {
            return this.key
        }

        internal fun type(): Class<*> {
            return this.type
        }
    }

    init {
        if (!contains(Settings.IS_FIRST_LAUNCH)) {
            setSetting(Settings.IS_FIRST_LAUNCH, true)
        }
    }


    operator fun contains(settingsKey: Settings): Boolean {
        return contains(settingsKey.key())
    }

    override fun contains(key: String): Boolean {
        return sPreferences.contains(key)
    }

    @Synchronized
    fun setSetting(settingsKey: Settings,
                   value: Any) {

        val settingsEditor = edit()
        var bChanged = false
        val key = settingsKey.key()
        val type = settingsKey.type()

        when (type) {
            String::class.java -> {
                settingsEditor.putString(key, value as String)
                bChanged = true
            }
            Boolean::class.java -> {
                settingsEditor.putBoolean(key, value as Boolean)
                bChanged = true
            }
            Int::class.java -> {
                settingsEditor.putInt(key, value as Int)
                bChanged = true
            }
            Long::class.java -> {
                settingsEditor.putLong(key, value as Long)
                bChanged = true
            }
            Float::class.java -> {
                settingsEditor.putFloat(key, value as Float)
                bChanged = true
            }
        }

        if (bChanged) {
            settingsEditor.apply()
        }
    }

    @Synchronized
    fun setSetting(settingsKey: Settings,
                   keyAdd: String,
                   value: Any) {

        val settingsEditor = edit()
        var bChanged = false
        val key = settingsKey.key()
        val type = settingsKey.type()

        when (type) {
            String::class.java -> {
                settingsEditor.putString(key + keyAdd, value as String)
                bChanged = true
            }
            Boolean::class.java -> {
                settingsEditor.putBoolean(key + keyAdd, value as Boolean)
                bChanged = true
            }
            Int::class.java -> {
                settingsEditor.putInt(key + keyAdd, value as Int)
                bChanged = true
            }
            Long::class.java -> {
                settingsEditor.putLong(key + keyAdd, value as Long)
                bChanged = true
            }
            Float::class.java -> {
                settingsEditor.putFloat(key + keyAdd, value as Float)
                bChanged = true
            }
        }

        if (bChanged) {
            settingsEditor.apply()
        }
    }

    override fun edit(): SharedPreferences.Editor {
        return sPreferences.edit()
    }

    override fun getAll(): Map<String, *> {
        return sPreferences.all
    }

    fun getBoolean(settingsKey: Settings): Boolean {
        return getBoolean(settingsKey.key(), false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sPreferences.getBoolean(key, defValue)
    }

    fun getFloat(settingsKey: Settings): Float {
        return getFloat(settingsKey.key(), 0f)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return sPreferences.getFloat(key, defValue)
    }

    fun getInt(settingsKey: Settings): Int {
        return getInt(settingsKey.key(), -1)
    }

    override fun getInt(key: String, defValue: Int): Int {
        return sPreferences.getInt(key, defValue)
    }

    fun getLong(settingsKey: Settings): Long {
        return getLong(settingsKey.key(), -1)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return sPreferences.getLong(key, defValue)
    }

    fun getString(settingsKey: Settings): String? {
        return getString(settingsKey.key(), null)
    }

    override fun getString(key: String, defValue: String?): String? {
        return sPreferences.getString(key, defValue)
    }

    override fun registerOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        return sPreferences.getStringSet(key, defValue)
    }
}
