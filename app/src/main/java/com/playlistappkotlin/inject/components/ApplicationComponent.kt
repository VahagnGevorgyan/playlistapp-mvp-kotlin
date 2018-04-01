package com.playlistappkotlin.inject.components

import android.content.Context
import android.content.res.Resources
import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.inject.modules.ApplicationModule
import com.playlistappkotlin.inject.qualifier.AppContext
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent : AppComponentProvides {

}

interface AppComponentProvides {
    @AppContext
    fun appContext(): Context
    fun resources(): Resources
    fun getDataManager(): IDataManager
}