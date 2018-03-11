package com.playlistappkotlin.di.component

import android.app.Application
import android.content.Context
import com.playlistappkotlin.App
import com.playlistappkotlin.data.IDataManager
import com.playlistappkotlin.di.ApplicationContext
import com.playlistappkotlin.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    val dataManager: IDataManager

    fun inject(app: App)

    //    void inject(GcmIntentService service);

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}