package com.playlistappkotlin.data.network.api

import android.annotation.SuppressLint
import com.playlistappkotlin.data.network.data.ApiResponse
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import retrofit2.Response
import timber.log.Timber

/**
 * Various data processing and validation methods for Rx chains.
 */
object ApiMethods {

    /**
     * Proceed with default validation chain.
     *
     * @param <T>
     * @return
    </T> */
    fun <T> validate(): Function<Response<T>, Observable<T>> {
        return Function(function = {
            Observable.just<Response<T>>(it)
                .flatMap(checkForRetrofitError())
                .flatMap(checkResponseForKOError())
                .flatMap(validateJSON()) })
    }

    /**
     * Validate returned data object.
     *
     * @param <T>
     * @return
    </T> */
    fun <T> validateJSON(): Function<Response<T>, ObservableSource<T>> {
        return Function(function = {
            Timber.d("Validating parsed data object values")
            Observable.just<T>(it.body())
        })
    }

    /**
     * Check for error being returned by Retrofit.
     */
    @SuppressLint("CheckResult")
    fun <T> checkForRetrofitError(): Function<Response<T>, ObservableSource<Response<T>>> {
        return Function(function = {
            Timber.d("Checking web service response for Retrofit error presence")
            if (!it.isSuccessful) {
                if (it.errorBody() != null) {
                    Timber.e(it.errorBody()!!.toString())
                    Observable.error<Response<T>>(Throwable(it.errorBody()!!.toString()))
                }
                Observable.error<Response<T>>(Throwable("Retrofit error is being present"))
            } else {
                if (it.body() != null) {
                    Timber.d("No Retrofit error is being present")
                    Observable.just<Response<T>>(it)
                } else {
                    Timber.e("Web service response body is null")
                    if (it.errorBody() != null) {
                        Observable.error<Response<T>>(Throwable(it.errorBody()!!.toString()))
                    } else Observable.error<Response<T>>(Throwable("Web service response body is null"))
                }
            }
        })
    }

    /**
     * Check if web service returned KO response.
     *
     * @param <T> must extend ApiResponse.
     * @return
    </T> */
    @SuppressLint("CheckResult")
    fun <T> checkResponseForKOError(): Function<Response<T>, ObservableSource<Response<T>>> {
        return Function(function = {
            Timber.d("Checking web service response for JSON \"KO\" error presence")

            val res = it.body() as ApiResponse
            if (!res.isSuccessful || !res.isOk) {
                Timber.d("JSON \"KO\" error is being present")
                Observable.error<Response<T>>(Throwable(res))
            }

            Timber.d("No JSON \"KO\" error is being present")

            Observable.just<Response<T>>(it)
        })
    }
}
