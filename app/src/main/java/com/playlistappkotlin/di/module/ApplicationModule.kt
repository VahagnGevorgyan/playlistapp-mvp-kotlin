package com.playlistappkotlin.di.module

import android.app.Application
import android.content.Context
import com.playlistappkotlin.R
import com.playlistappkotlin.data.DataManager
import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.di.PreferenceInfo
import com.playlistappkotlin.di.api.ApiModule
import com.playlistappkotlin.di.db.DatabaseModule
import com.playlistappkotlin.di.settings.SettingsModule
import com.playlistappkotlin.ext.Constants.Companion.PREF_NAME
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module(includes = [(ApiModule::class), (DatabaseModule::class), (SettingsModule::class)])
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return PREF_NAME
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: DataManager): IDataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideCalligraphyDefaultConfig(): CalligraphyConfig {
        return CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/fonts.source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
    }
}
