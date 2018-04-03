package com.playlistappkotlin

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.playlistappkotlin.eventbus.SingletonBus
import com.playlistappkotlin.ext.logging.DevelopmentTree
import com.playlistappkotlin.ext.logging.ProductionTree
import com.playlistappkotlin.inject.components.ApplicationComponent
import com.playlistappkotlin.inject.components.DaggerApplicationComponent
import com.playlistappkotlin.inject.modules.ApplicationModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber

open class App: Application() {

    private lateinit var mContext: App


//    @Inject
//    protected lateinit var mCalligraphyConfig: CalligraphyConfig

    companion object {
        lateinit var mAppComponent: ApplicationComponent
            private set
    }

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
//        mAppComponent.inject(this)
    }

    protected fun buildComponent(): ApplicationComponent {
        Timber.d("Build App Component")
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
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
//        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }
}