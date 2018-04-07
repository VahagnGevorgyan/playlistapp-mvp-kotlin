package com.playlistappkotlin.ui.home.settings

import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.ui.base.BasePresenter
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class SettingsPresenter @Inject constructor(dataManager: IDataManager,
                                            schedulerProvider: ISchedulerProvider,
                                            compositeDisposable: CompositeDisposable)
    : BasePresenter(dataManager, schedulerProvider, compositeDisposable), SettingsMvpPresenter {

    override fun loadCountries() {
        if (!isViewAttached) {
            return
        }
        mvpView?.showLoading()
        val locale = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        var country: String
        for (loc in locale) {
            country = loc.displayCountry
            if (country.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER)
        (mvpView as SettingsMvpView).updateCountries(countries)
        mvpView?.hideLoading()
    }

    override fun loadCountryIndex(countries: List<String>) {
        if (!isViewAttached) {
            return
        }
        for (i in countries.indices) {
            if (countries[i] == dataManager.settingsHelper.search().country) {
                (mvpView as SettingsMvpView).setSelectedCountry(i)
                return
            }
        }
    }

    override fun loadLimitIndex(limitItems: List<String>) {
        if (!isViewAttached) {
            return
        }
        for (i in limitItems.indices) {
            if (limitItems[i] == dataManager.settingsHelper.search().limitCount.toString()) {
                (mvpView as SettingsMvpView).setSelectedLimit(i)
                return
            }
        }
    }

    override fun onSaveClicked(selectedCountry: String, countryDefValue: String, selectedLimit: String, limitDefValue: String) {
        if (!isViewAttached) {
            return
        }
        if (isFormValid(selectedCountry, countryDefValue, selectedLimit, limitDefValue)) {
            mvpView?.showLoading()

            dataManager.settingsHelper.search().country = selectedCountry
            dataManager.settingsHelper.search().limitCount = Integer.valueOf(selectedLimit)

            mvpView?.hideLoading()
            (mvpView as SettingsMvpView).backToTracks()
        }
    }

    private fun isFormValid(city: String, defCity: String, limit: String, defLimit: String): Boolean {
        var isValid = true
        if (!validateCountry(city, defCity)) {
            isValid = false
        }
        if (!validateLimit(limit, defLimit)) {
            isValid = false
        }
        return isValid
    }

    private fun validateLimit(limit: String?, defValue: String): Boolean {
        var isValid = true
        if (limit != null && limit != defValue) {
            (mvpView as SettingsMvpView).clearLimitNotSelectedError()
        } else {
            isValid = false
            (mvpView as SettingsMvpView).showLimitNotSelectedError()
        }
        return isValid
    }

    private fun validateCountry(country: String?, defValue: String): Boolean {
        var isValid = true
        if (country != null && country != defValue) {
            (mvpView as SettingsMvpView).clearCountryNotSelectedError()
        } else {
            isValid = false
            (mvpView as SettingsMvpView).showCountryNotSelectedError()
        }
        return isValid
    }
}
