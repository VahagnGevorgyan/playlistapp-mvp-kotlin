package com.playlistappkotlin.ui.home.settings

import com.playlistappkotlin.ui.base.MvpView

interface SettingsMvpView : MvpView {
    fun updateCountries(countries: MutableList<String>)

    fun clearCountryNotSelectedError()

    fun showCountryNotSelectedError()

    fun clearLimitNotSelectedError()

    fun showLimitNotSelectedError()

    fun setSelectedCountry(i: Int)

    fun setSelectedLimit(i: Int)

    fun backToTracks()
}
