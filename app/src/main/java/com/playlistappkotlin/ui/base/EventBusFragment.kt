package com.playlistappkotlin.ui.base

import android.support.v4.app.Fragment
import com.playlistappkotlin.eventbus.SingletonBus
import timber.log.Timber

open class EventBusFragment : Fragment() {

    override fun onStart() {
        super.onStart()
        SingletonBus.instance.register(this)
    }

    override fun onStop() {
        try {
            SingletonBus.instance.unregister(this)
        } catch (e: IllegalStateException) {
            Timber.e(e.localizedMessage)
        }
        super.onStop()
    }
}
