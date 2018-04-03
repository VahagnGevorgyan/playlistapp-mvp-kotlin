package com.playlistappkotlin.inject.components

import android.content.Context
import android.support.v4.app.FragmentManager
import com.playlistappkotlin.inject.modules.ActivityModule
import com.playlistappkotlin.inject.qualifier.ActivityFragmentManager
import com.playlistappkotlin.inject.scopes.PerActivity
import com.playlistappkotlin.inject.qualifier.ActivityContext
import com.playlistappkotlin.ui.home.HomeActivity
import com.playlistappkotlin.ui.home.settings.SettingsFragment
import com.playlistappkotlin.ui.splash.SplashActivity
import dagger.Component

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [ActivityModule::class])
interface ActivityComponent : ActivityComponentProvides {

    fun inject(activity: HomeActivity)
    fun inject(activity: SplashActivity)
    fun inject(fragment: SettingsFragment)
}

interface ActivityComponentProvides : AppComponentProvides {
    @ActivityContext
    fun activityContext(): Context
    @ActivityFragmentManager
    fun defaultFragmentManager(): FragmentManager
}