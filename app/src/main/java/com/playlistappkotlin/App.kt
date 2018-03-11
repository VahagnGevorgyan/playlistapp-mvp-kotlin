package com.playlistappkotlin

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.playlistappkotlin.di.api.ApiModule
import com.playlistappkotlin.di.component.ApplicationComponent
import com.playlistappkotlin.di.component.DaggerApplicationComponent
import com.playlistappkotlin.di.module.ApplicationModule
import com.playlistappkotlin.eventbus.SingletonBus
import com.playlistappkotlin.ext.logging.DevelopmentTree
import com.playlistappkotlin.ext.logging.ProductionTree
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import javax.inject.Inject

open class App: Application() {

    protected lateinit var mContext: App

    @Inject
    protected lateinit var mCalligraphyConfig: CalligraphyConfig

    protected lateinit var mAppComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        mContext = this

        initializeEventBus()
        initializeFabric()
        initializeInjector()
        initializeCalligraphy()
    }

    private fun initializeEventBus() {
        Timber.d("Initializing EventBus")

        SingletonBus.initialize()
    }

    /**
     * Initializing Injector.
     */
    private fun initializeInjector() {
        Timber.d("Initializing ApplicationComponent")
        mAppComponent = buildComponent()
        mAppComponent.inject(this)
    }

    protected fun buildComponent(): ApplicationComponent {
        Timber.d("Build App Component")
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .apiModule(ApiModule(this))
                .build()
    }

    /**
     * Initializing [Fabric] + [Crashlytics] + [Timber]
     */
    private fun initializeFabric() {
        Timber.d("Initializing Fabric + Crashlytics + Timber")

        Fabric.with(this, Crashlytics())
        Fabric.with(this, Answers(), Crashlytics())
        if (BuildConfig.DEBUG) {
            Timber.plant(DevelopmentTree())
        } else {
            Timber.plant(ProductionTree())
        }
    }

    /**
     * Initializing [uk.co.chrisjenx.calligraphy.CalligraphyConfig].
     */
    private fun initializeCalligraphy() {
        Timber.d("Initializing CalligraphyConfig")
        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }
}