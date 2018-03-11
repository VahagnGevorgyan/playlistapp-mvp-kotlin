package com.playlistappkotlin.data.settings

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.di.PreferenceInfo

/**
 * Various methods for storing data in shared preferences.
 */
@Singleton
class AppPreferences @Inject
constructor(@ApplicationContext context: Context,
            @PreferenceInfo prefFileName: String) : SharedPreferences {

    private val sPreferences: SharedPreferences?

    /**
     * Settings keys.
     */
    enum class Settings private constructor(private val key: String, private val type: Class<*>) {
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
        sPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

        if (!contains(Settings.IS_FIRST_LAUNCH)) {
            setSetting(Settings.IS_FIRST_LAUNCH, true)
        }
    }


    operator fun contains(settingsKey: Settings): Boolean {
        return contains(settingsKey.key())
    }

    override fun contains(key: String): Boolean {
        var retval = false

        if (null != sPreferences) {
            retval = sPreferences.contains(key)
        }

        return retval
    }

    @Synchronized
    fun setSetting(settingsKey: Settings,
                   value: Any) {

        val settingsEditor = edit()
        var bChanged = false
        val key = settingsKey.key()
        val type = settingsKey.type()

        if (null != settingsEditor) {
            if (String::class.java == type) {
                settingsEditor.putString(key, value as String)
                bChanged = true
            } else if (Boolean::class.java == type) {
                settingsEditor.putBoolean(key, value as Boolean)
                bChanged = true
            } else if (Int::class.java == type) {
                settingsEditor.putInt(key, value as Int)
                bChanged = true
            } else if (Long::class.java == type) {
                settingsEditor.putLong(key, value as Long)
                bChanged = true
            } else if (Float::class.java == type) {
                settingsEditor.putFloat(key, value as Float)
                bChanged = true
            }

            if (bChanged) {
                settingsEditor.commit()
            }
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

        if (null != settingsEditor) {
            if (String::class.java == type) {
                settingsEditor.putString(key + keyAdd, value as String)
                bChanged = true
            } else if (Boolean::class.java == type) {
                settingsEditor.putBoolean(key + keyAdd, value as Boolean)
                bChanged = true
            } else if (Int::class.java == type) {
                settingsEditor.putInt(key + keyAdd, value as Int)
                bChanged = true
            } else if (Long::class.java == type) {
                settingsEditor.putLong(key + keyAdd, value as Long)
                bChanged = true
            } else if (Float::class.java == type) {
                settingsEditor.putFloat(key + keyAdd, value as Float)
                bChanged = true
            }

            if (bChanged) {
                settingsEditor.commit()
            }
        }
    }

    override fun edit(): SharedPreferences.Editor? {
        var editor: SharedPreferences.Editor? = null

        if (null != sPreferences) {
            editor = sPreferences.edit()
        }

        return editor
    }

    override fun getAll(): Map<String, *> {
        var map: Map<String, *> = emptyMap<String, Any>()

        if (null != sPreferences) {
            map = sPreferences.all
        }

        return map
    }

    fun getBoolean(settingsKey: Settings): Boolean {
        return getBoolean(settingsKey.key(), false)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getBoolean(key, defValue)
        }

        return retval
    }

    fun getFloat(settingsKey: Settings): Float {
        return getFloat(settingsKey.key(), 0f)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getFloat(key, defValue)
        }

        return retval
    }

    fun getInt(settingsKey: Settings): Int {
        return getInt(settingsKey.key(), -1)
    }

    override fun getInt(key: String, defValue: Int): Int {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getInt(key, defValue)
        }

        return retval
    }

    fun getLong(settingsKey: Settings): Long {
        return getLong(settingsKey.key(), -1)
    }

    override fun getLong(key: String, defValue: Long): Long {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getLong(key, defValue)
        }

        return retval
    }

    fun getString(settingsKey: Settings): String? {
        return getString(settingsKey.key(), null)
    }

    override fun getString(key: String, defValue: String?): String? {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getString(key, defValue)
        }

        return retval
    }

    override fun registerOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {

        sPreferences?.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
            listener: SharedPreferences.OnSharedPreferenceChangeListener) {

        sPreferences?.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun getStringSet(key: String, defValue: Set<String>?): Set<String>? {
        var retval = defValue

        if (null != sPreferences) {
            retval = sPreferences.getStringSet(key, defValue)
        }

        return retval
    }

}