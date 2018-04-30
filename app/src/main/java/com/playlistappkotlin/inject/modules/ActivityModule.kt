package com.playlistappkotlin.inject.modules

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.data.scheduler.SchedulerProvider
import com.playlistappkotlin.ext.network.NetworkStateHelper
import com.playlistappkotlin.ext.network.NetworkStateManager
import com.playlistappkotlin.inject.qualifier.ActivityContext
import com.playlistappkotlin.inject.qualifier.ActivityFragmentManager
import com.playlistappkotlin.inject.scopes.PerActivity
import com.playlistappkotlin.ui.adapter.TrackListAdapter
import com.playlistappkotlin.ui.home.HomeMvpPresenter
import com.playlistappkotlin.ui.home.HomePresenter
import com.playlistappkotlin.ui.home.about.AboutMvpPresenter
import com.playlistappkotlin.ui.home.about.AboutPresenter
import com.playlistappkotlin.ui.home.favorite.FavoritesMvpPresenter
import com.playlistappkotlin.ui.home.favorite.FavoritesPresenter
import com.playlistappkotlin.ui.home.settings.SettingsMvpPresenter
import com.playlistappkotlin.ui.home.settings.SettingsPresenter
import com.playlistappkotlin.ui.home.tracks.TracksMvpPresenter
import com.playlistappkotlin.ui.home.tracks.TracksPresenter
import com.playlistappkotlin.ui.splash.SplashMvpPresenter
import com.playlistappkotlin.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule constructor (private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    @ActivityContext
    internal fun provideActivityContext(): Context = activity

    @Provides
    @PerActivity
    @ActivityFragmentManager
    internal fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    @PerActivity
    internal fun provideActivity(): AppCompatActivity {
        return activity
    }

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    internal fun provideSchedulerProvider(): ISchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    @PerActivity
    internal fun provideNetworkHelper(
            networkStateManager: NetworkStateManager): NetworkStateHelper {
        return networkStateManager
    }

    @Provides
    internal fun provideLinearLayoutManager(activity: AppCompatActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    @PerActivity
    internal fun provideSplashPresenter(
            presenter: SplashPresenter): SplashMvpPresenter {
        return presenter
    }

    @Provides
    @PerActivity
    internal fun provideHomePresenter(
            presenter: HomePresenter): HomeMvpPresenter {
        return presenter
    }

    @Provides
    @PerActivity
    internal fun provideSettingsPresenter(
            presenter: SettingsPresenter): SettingsMvpPresenter {
        return presenter
    }

    @Provides
    @PerActivity
    internal fun provideAboutPresenter(
            presenter: AboutPresenter): AboutMvpPresenter {
        return presenter
    }

    @Provides
    @PerActivity
    internal fun provideTracksPresenter(
            presenter: TracksPresenter): TracksMvpPresenter {
        return presenter
    }

    @Provides
    @PerActivity
    internal fun provideTrackListAdapter(activity: AppCompatActivity): TrackListAdapter {
        return TrackListAdapter(activity)
    }

    @Provides
    @PerActivity
    internal fun provideFavoritesPresenter(
            presenter: FavoritesPresenter): FavoritesMvpPresenter {
        return presenter
    }
}
