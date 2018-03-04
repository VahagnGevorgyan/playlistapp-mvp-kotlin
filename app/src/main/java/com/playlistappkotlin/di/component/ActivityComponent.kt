package com.playlistappkotlin.di.component

import com.playlistappkotlin.di.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

//    fun inject(activity: SplashActivity)
//
//    fun inject(activity: HomeActivity)
//
//    fun inject(fragment: TracksFragment)
//
//    fun inject(fragment: SettingsFragment)
//
//    fun inject(fragment: AboutFragment)
//
//    fun inject(fragment: FavoritesFragment)
//
//    fun inject(activity: WebViewActivity)
//
//    fun inject(networkStateManager: NetworkStateManager)

}
