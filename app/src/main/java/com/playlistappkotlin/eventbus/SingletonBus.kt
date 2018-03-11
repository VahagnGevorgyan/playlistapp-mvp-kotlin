package com.playlistappkotlin.eventbus

import com.squareup.otto.Bus
import java.util.*

class SingletonBus {

    private var mBus: Bus = Bus()

    companion object {
        lateinit var instance: SingletonBus

        fun initialize() {
            instance = SingletonBus()
        }
    }

    private val eventQueueBuffer = LinkedList<Any>()

    private var paused: Boolean = false

    fun <T> post(event: T) {
        if (paused) {
            eventQueueBuffer.add(event!!)
        } else {
            mBus.post(event)
        }
    }

    fun <T> register(subscriber: T) {
        mBus.register(subscriber)
    }

    fun <T> unregister(subscriber: T) {
        mBus.unregister(subscriber)
    }

    fun isPaused(): Boolean {
        return paused
    }

    fun setPaused(paused: Boolean) {
        this.paused = paused
        if (!paused) {
            val eventIterator = eventQueueBuffer.iterator()
            while (eventIterator.hasNext()) {
                val event = eventIterator.next()
                post(event)
                eventIterator.remove()
            }
        }
    }
}
