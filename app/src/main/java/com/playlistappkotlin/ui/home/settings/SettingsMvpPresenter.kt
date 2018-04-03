package com.playlistappkotlin.ui.home.settings

import com.playlistappkotlin.ui.base.MvpPresenter

interface SettingsMvpPresenter : MvpPresenter {

    fun loadCountries()

    fun loadCountryIndex(countries: List<String>)

    fun loadLimitIndex(limitItems: List<String>)

    fun onSaveClicked(selectedCountry: String, countryDefValue: String, selectedLimit: String, limitDefValue: String)
}
