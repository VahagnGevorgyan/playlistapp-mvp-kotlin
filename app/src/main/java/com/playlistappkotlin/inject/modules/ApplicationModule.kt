package com.playlistappkotlin.inject.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.playlistappkotlin.R
import com.playlistappkotlin.data.DataManager
import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.settings.AppSettingsHelper
import com.playlistappkotlin.data.settings.IAppSettingsHelper
import com.playlistappkotlin.ext.Constants
import com.playlistappkotlin.inject.qualifier.AppContext
import com.playlistappkotlin.inject.qualifier.PreferenceInfo
import com.playlistappkotlin.inject.scopes.PerApplication
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Singleton

@Module(includes = [ApiModule::class, DatabaseModule::class])
class ApplicationModule(private val app: Application) {

    @Provides
    @Singleton
    @AppContext
    internal fun provideAppContext(): Context = app

    @Provides
    @Singleton
    internal fun provideResources(): Resources = app.resources

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return Constants.PREF_NAME
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: DataManager): IDataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideAppSettingsHelper(appAppSettingsHelper: AppSettingsHelper): IAppSettingsHelper {
        return appAppSettingsHelper
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
