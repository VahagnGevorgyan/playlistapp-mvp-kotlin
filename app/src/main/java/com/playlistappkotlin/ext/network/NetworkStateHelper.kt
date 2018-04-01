package com.playlistappkotlin.ext.network

import com.playlistappkotlin.inject.scopes.PerActivity

@PerActivity
interface NetworkStateHelper {

    fun initializeNetworkStatusListener()

    fun recheckNetwork()
}

