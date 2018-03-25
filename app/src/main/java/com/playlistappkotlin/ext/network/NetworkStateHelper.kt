package com.playlistappkotlin.ext.network

import com.playlistappkotlin.di.PerActivity

@PerActivity
interface NetworkStateHelper {

    fun initializeNetworkStatusListener()

    fun recheckNetwork()
}

