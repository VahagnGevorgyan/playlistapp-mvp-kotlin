package com.playlistappkotlin.data.network.api

import com.playlistappkotlin.data.network.data.track.TrackResData
import com.playlistappkotlin.data.network.data.track.TrackResponse
import com.playlistappkotlin.data.network.session.Session
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.HttpUrl
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides data for the app.
 */
@Singleton
class ApiHelper @Inject constructor(private val mApiService: ApiInterface,
                                    private val mSession: Session,
                                    baseUrl: HttpUrl
) {
    /**
     * Returns base url for web services calls.
     *
     * @return
     */
    val baseUrl: String = baseUrl.toString()

    /**
     * Returns Tracks response.
     *
     * @return
     */
    fun doTrackListCall(country: String,
                        limit: Int?,
                        page: Int?): Observable<TrackResData> {
        val url = mSession.trackListServiceURL
        Timber.d("Requesting Track List web service with url: " + url)

        return mApiService.callTrackListApi(url, country, limit, page)
                .flatMap(ApiMethods.validate())
                .flatMap(processTrackListResponse())
    }


    // PROCESSING RESULT

    /**
     * Processing received Tracks List Response data.
     *
     * @return
     */
    private fun processTrackListResponse(): Function<TrackResponse, ObservableSource<TrackResData>> {
        Timber.d("Processing received Track List Response data")
        return Function(function = { Observable.just(it.data) })
    }
}
