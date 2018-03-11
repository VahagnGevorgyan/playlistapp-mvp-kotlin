package com.playlistappkotlin.di.settings

import com.playlistappkotlin.data.settings.AppSettingsHelper
import com.playlistappkotlin.data.settings.IAppSettingsHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module which provides Settings.
 */
@Module
class SettingsModule {

    @Provides
    @Singleton
    internal fun provideAppSettingsHelper(appAppSettingsHelper: AppSettingsHelper): IAppSettingsHelper {
        return appAppSettingsHelper
    }
}
