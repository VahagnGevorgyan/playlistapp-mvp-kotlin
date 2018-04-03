package com.playlistappkotlin.ui.home

import android.os.Bundle
import android.view.View
import com.playlistappkotlin.eventbus.SingletonBus
import com.playlistappkotlin.eventbus.event.SetMainFragmentDetailsEvent
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_FRAGMENT_POSITION
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_MENU_ITEM_ID
import com.playlistappkotlin.ui.base.BaseFragment
import timber.log.Timber

abstract class HomeBaseFragment : BaseFragment() {

    protected var mFragmentPosition: Int = 0
    protected var mMenuItemPosition: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragmentAppearanceDetails()
    }

    /**
     * Initializing previous fragment appearance details.
     */
    protected fun initFragmentAppearanceDetails() {
        val bundle = arguments
        bundle?.let {
            mFragmentPosition = it.getInt(EXTRA_FRAGMENT_POSITION, -1)
            mMenuItemPosition = it.getInt(EXTRA_MENU_ITEM_ID, 0)
        } ?: run {
            mFragmentPosition = 0
            mMenuItemPosition = 0
        }
    }

    override fun onResume() {
        super.onResume()
        setFragmentAppearanceDetails()
    }

    /**
     * Method for setting main fragment appearance details.
     */
    protected fun setFragmentAppearanceDetails() {
        val event = SetMainFragmentDetailsEvent(mFragmentPosition, mMenuItemPosition)
        try {
            SingletonBus.instance.post(event)
        } catch (e: Exception) {
            // Could happen when setting fragment at a wrong time.
            Timber.e("Failed to setting main fragment appearance details " + e.localizedMessage)
        }

    }
}