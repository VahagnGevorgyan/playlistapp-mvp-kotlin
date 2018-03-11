package com.playlistappkotlin.data.network.api

import com.playlistappkotlin.data.network.data.track.TrackResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun callTrackListApi(@Url requestUrl: String,
                         @Query("country") country: String,
                         @Query("limit") limit: Int?,
                         @Query("page") page: Int?
    ): Observable<Response<TrackResponse>>
}
