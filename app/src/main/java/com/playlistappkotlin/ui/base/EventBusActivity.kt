package com.playlistappkotlin.ui.base

import android.support.v7.app.AppCompatActivity
import com.playlistappkotlin.eventbus.SingletonBus
import timber.log.Timber

abstract class EventBusActivity : AppCompatActivity() {

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

    override fun onBackPressed() {
        try {
            super.onBackPressed()
        } catch (e: IllegalStateException) {
            Timber.e(e.localizedMessage)
        }

    }
}

