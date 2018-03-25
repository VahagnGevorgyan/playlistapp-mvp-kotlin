package com.playlistappkotlin.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.playlistappkotlin.data.scheduler.ISchedulerProvider
import com.playlistappkotlin.data.scheduler.SchedulerProvider
import com.playlistappkotlin.di.ActivityContext
import com.playlistappkotlin.di.PerActivity
import com.playlistappkotlin.ext.network.NetworkStateHelper
import com.playlistappkotlin.ext.network.NetworkStateManager
import com.playlistappkotlin.ui.home.HomeMvpPresenter
import com.playlistappkotlin.ui.home.HomeMvpView
import com.playlistappkotlin.ui.home.HomePresenter
import com.playlistappkotlin.ui.splash.SplashMvpPresenter
import com.playlistappkotlin.ui.splash.SplashMvpView
import com.playlistappkotlin.ui.splash.SplashPresenter
import com.playlistappkotlin.ui.web.WebViewMvpPresenter
import com.playlistappkotlin.ui.web.WebViewMvpView
import com.playlistappkotlin.ui.web.WebViewPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(@get:Provides
                     val mActivity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
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
    fun provideSplashPresenter(
            presenter: SplashPresenter<SplashMvpView>): SplashMvpPresenter<SplashMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideHomePresenter(
            presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> {
        return presenter
    }

    @Provides
    @PerActivity
    fun provideWebViewPresenter(
            presenter: WebViewPresenter<WebViewMvpView>): WebViewMvpPresenter<WebViewMvpView> {
        return presenter
    }

//    @Provides
//    @PerActivity
//    internal fun provideTracksPresenter(
//            presenter: TracksPresenter<TracksMvpView>): TracksMvpPresenter<TracksMvpView> {
//        return presenter
//    }

//    @Provides
//    internal fun provideTrackListAdapter(activity: AppCompatActivity): TrackListAdapter {
//        return TrackListAdapter(activity)
//    }
//
//    @Provides
//    @PerActivity
//    internal fun provideSettingsPresenter(
//            presenter: SettingsPresenter<SettingsMvpView>): SettingsMvpPresenter<SettingsMvpView> {
//        return presenter
//    }
//
//    @Provides
//    @PerActivity
//    internal fun provideAboutPresenter(
//            presenter: AboutPresenter<AboutMvpView>): AboutMvpPresenter<AboutMvpView> {
//        return presenter
//    }
//
//    @Provides
//    @PerActivity
//    internal fun provideFavoritesPresenter(
//            presenter: FavoritesPresenter<FavoritesMvpView>): FavoritesMvpPresenter<FavoritesMvpView> {
//        return presenter
//    }

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
}
