package com.playlistappkotlin.ui.home.settings

import android.os.Bundle
import android.support.annotation.ColorInt
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import butterknife.*
import com.playlistappkotlin.R
import com.playlistappkotlin.eventbus.SingletonBus
import com.playlistappkotlin.eventbus.event.RefreshTracksEvent
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_FRAGMENT_POSITION
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_MENU_ITEM_ID
import com.playlistappkotlin.ext.FragmentUtils.SETTINGS_POSITION
import com.playlistappkotlin.ui.adapter.CustomSpinnerAdapter
import com.playlistappkotlin.ui.home.HomeBaseFragment
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class SettingsFragment : HomeBaseFragment(), SettingsMvpView {

    @Inject
    lateinit var mPresenter: SettingsMvpPresenter

    @BindView(R.id.spinnerCountry)
    lateinit var mSpinnerCountry: Spinner
    @BindView(R.id.dividerSpinnerCountry)
    lateinit var mDividerSpinnerCountry: View
    @BindView(R.id.textViewSpinnerCountryError)
    lateinit var mTextViewSpinnerCountryError: TextView
    @BindView(R.id.spinnerLimit)
    lateinit var mSpinnerLimit: Spinner
    @BindView(R.id.dividerSpinnerLimit)
    lateinit var mDividerSpinnerLimit: View
    @BindView(R.id.textViewSpinnerLimitError)
    lateinit var mTextViewSpinnerLimitError: TextView

    @BindString(R.string.str_select_country)
    lateinit var mCountryDefValue: String
    @BindString(R.string.str_select_limit)
    lateinit var mLimitDefValue: String
    @BindString(R.string.str_error_empty_text)
    lateinit var mErrorEmptyText: String

    @BindColor(R.color.black)
    @ColorInt
    @JvmField var mColorBlack: Int = 0
    @BindColor(R.color.error_color)
    @ColorInt
    @JvmField var mColorError: Int = 0

    private lateinit var mCountryAdapter: CustomSpinnerAdapter
    private lateinit var mLimitAdapter: CustomSpinnerAdapter

    override val rootViewId: Int
        get() = R.layout.fragment_settings

    override fun initInjector(view: View) {
        Timber.d("Injecting \"Settings\" fragment")
        activityComponent?.inject(this)
        setUnBinder(ButterKnife.bind(this, view))
        mPresenter.onAttach(this)
    }

    override fun prepareView(rootView: View) {
        Timber.d("Preparing fragment elements")
        prepareCountrySpinner()
        prepareLimitSpinner()
        mPresenter.loadCountries()
    }

    private fun prepareCountrySpinner() {
        Timber.d("Preparing country spinner adapter")
        val countryItems = ArrayList<String>()
        countryItems.add(0, mCountryDefValue)
        baseActivity?.let {
            mCountryAdapter = CustomSpinnerAdapter(it, R.layout.spinner_selection_item, countryItems)
            mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinnerCountry.adapter = mCountryAdapter
            mSpinnerCountry.setSelection(0)
            mSpinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    mTextViewSpinnerCountryError.visibility = View.GONE
                    mDividerSpinnerCountry.setBackgroundColor(mColorBlack)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Nothing selected.
                }
            }
        }
    }

    private fun prepareLimitSpinner() {
        Timber.d("Preparing limit spinner adapter")
        val limitResArray = getResources().getStringArray(R.array.limit_string_array)
        val limitItemsList = Arrays.asList<String>(*limitResArray)
        val limitItems = ArrayList(limitItemsList)
        limitItems.add(0, mLimitDefValue)
        baseActivity?.let {
            mLimitAdapter = CustomSpinnerAdapter(it, R.layout.spinner_selection_item, limitItems)
            mLimitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinnerLimit.adapter = mLimitAdapter
            mSpinnerLimit.setSelection(0)
            mSpinnerLimit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    mTextViewSpinnerLimitError.visibility = View.GONE
                    activity?.let { mDividerSpinnerLimit.setBackgroundColor(mColorBlack) }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Nothing selected.
                }
            }
        }
        mPresenter.loadLimitIndex(limitItems)
    }

    override fun updateCountries(countries: MutableList<String>) {
        Timber.d("Updating countries spinner items $countries")
        countries.add(0, mCountryDefValue)
        mCountryAdapter.updateItems(countries)
        mPresenter.loadCountryIndex(countries)
    }

    override fun clearCountryNotSelectedError() {
        mTextViewSpinnerCountryError.visibility = View.GONE
        mDividerSpinnerCountry.setBackgroundColor(mColorBlack)
    }

    override fun showCountryNotSelectedError() {
        mTextViewSpinnerCountryError.visibility = View.VISIBLE
        mDividerSpinnerCountry.setBackgroundColor(mColorError)
    }

    override fun clearLimitNotSelectedError() {
        mTextViewSpinnerLimitError.visibility = View.GONE
        mDividerSpinnerLimit.setBackgroundColor(mColorBlack)
    }

    override fun showLimitNotSelectedError() {
        mTextViewSpinnerLimitError.visibility = View.VISIBLE
        mDividerSpinnerLimit.setBackgroundColor(mColorError)
    }

    override fun setSelectedCountry(i: Int) {
        Timber.d("Setting selected country $i")
        mSpinnerCountry.setSelection(i)
    }

    override fun setSelectedLimit(i: Int) {
        Timber.d("Setting selected limit $i")
        mSpinnerLimit.setSelection(i)
    }

    override fun backToTracks() {
        Timber.d("Back to tracks and refresh list")
        SingletonBus.instance.post(RefreshTracksEvent())
        baseActivity?.onFragmentDetached(TAG)
    }

    @OnClick(R.id.nav_back_btn)
    internal fun onNavBackClick() {
        baseActivity?.onFragmentDetached(TAG)
    }

    @OnClick(R.id.save_btn)
    internal fun onCheckClick() {
        Timber.d("Save button is clicked")
        var selectedCountry = ""
        if (mSpinnerCountry.selectedItem != null) {
            selectedCountry = mSpinnerCountry.selectedItem as String
        }
        var selectedLimit = ""
        if (mSpinnerLimit.selectedItem != null) {
            selectedLimit = mSpinnerLimit.selectedItem as String
        }
        mPresenter.onSaveClicked(
                selectedCountry,
                mCountryDefValue,
                selectedLimit,
                mLimitDefValue
        )
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

    companion object {

        val TAG: String = SettingsFragment::class.java.simpleName

        fun newInstance(id: Int): SettingsFragment {
            val args = Bundle()
            args.putInt(EXTRA_FRAGMENT_POSITION, SETTINGS_POSITION)
            args.putInt(EXTRA_MENU_ITEM_ID, id)
            val fragment = SettingsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}