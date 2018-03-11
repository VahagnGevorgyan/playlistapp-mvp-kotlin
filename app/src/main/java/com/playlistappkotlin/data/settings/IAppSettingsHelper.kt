package com.playlistappkotlin.data.settings

interface IAppSettingsHelper {

    fun general(): GeneralSettings

    fun search(): SearchSettings
}
