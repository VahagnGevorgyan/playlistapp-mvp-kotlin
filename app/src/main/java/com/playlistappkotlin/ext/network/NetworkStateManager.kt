package com.playlistappkotlin.ext.network

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.playlistappkotlin.ext.Constants.Companion.TIMEOUT_RETRY_INITIALIZING_NETWORK_LISTENER
import com.playlistappkotlin.ui.offline.OfflineDialog
import com.playlistappkotlin.ui.splash.SplashActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Utils for networking stuff - check status etc.
 */
class NetworkStateManager @Inject
constructor(private val mActivity: AppCompatActivity?) : NetworkStateHelper {

    private var mInitializedNetworkMonitoring: Boolean? = false
    private var mIsConnectedToInternet: Boolean = false
    var networkType: Int = 0
        private set
    private val mInitializedNetworkMonitoringLock = Any()
    private val mConnectedLock = Any()
    private val mInitializedNetworkObservable = BehaviorSubject.create<Boolean>()
    private val mIsInternetAvailableObservable = BehaviorSubject.create<Boolean>()

    /**
     * Tells if the network connectivity monitoring has been initialized.
     *
     * @return
     */
    /**
     * Sets network connectivity monitoring initialization status.
     *
     * @param initializedNetworkMonitoring
     */
    var isNetworkMonitoringInitialized: Boolean
        get() = synchronized(mInitializedNetworkMonitoringLock) {
            return mInitializedNetworkMonitoring!!
        }
        set(initializedNetworkMonitoring) = synchronized(mInitializedNetworkMonitoringLock) {
            Timber.d("Sets network connectivity monitoring initialization status to: $initializedNetworkMonitoring")
            mInitializedNetworkMonitoring = initializedNetworkMonitoring
            mInitializedNetworkObservable.onNext(initializedNetworkMonitoring)
        }

    /**
     * Returns network connectivity monitoring initialization status Observable.
     *
     * @return
     */
    val isNetworkMonitoringInitializedObservable: Observable<Boolean>
        get() = mInitializedNetworkObservable

    /**
     * Returns Internet availability monitoring Observable.
     *
     * @return
     */
    val isInternetAvailableObservable: Observable<Boolean>
        get() = mIsInternetAvailableObservable

    /**
     * Tells if the device currently is connected to the Internet.
     *
     * @return Is the device currently connected to the Internet?
     */
    val isConnectedToInternet: Boolean
        get() = synchronized(mConnectedLock) {
            Timber.d("Is the device connected to the Internet? $mIsConnectedToInternet")
            return mIsConnectedToInternet
        }

    /**
     * Network types.
     */
    enum class NetworkType (val type: String) {
        WIFI("WIFI"), MOBILE("WIFI"), NOT_CONNECTED("WIFI")
    }

    /**
     * Starts Network State monitoring.
     */
    override fun initializeNetworkStatusListener() {
        Timber.d("Starts Network State monitoring")
        ReactiveNetwork.observeNetworkConnectivity(mActivity!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ connectivity ->
                    val connectivityState = connectivity.state
                    val isConnected = connectivityState == NetworkInfo.State.CONNECTED
                    Timber.d("Internet connectivity status changed to: " + connectivity.toString())
                    Timber.d("Interpreted it as: is connected? $isConnected")
                    setConnectedToInternet(isConnected, connectivity.type)
                    if (!isNetworkMonitoringInitialized) {
                        isNetworkMonitoringInitialized = true
                    }
                    recheckNetwork()
                }
                ) { throwable ->
                    Timber.e(throwable.message)
                    if (!isNetworkMonitoringInitialized) {
                        retryInitializingNetworkStatusListener()
                    }
                }
    }

    /**
     * Checks if has to show "No Internet" popup.
     */
    override fun recheckNetwork() {
        Timber.d("Checks if has to show \"No Internet\" popup")
        if (!isConnectedToInternet
                && mActivity != null
                && mActivity !is SplashActivity
                && !OfflineDialog.alreadyOnTheScreen()) {
            Timber.d("Showing \"No Internet\" popup")

            OfflineDialog.showNoInternetPopup(mActivity, Runnable { this.recheckNetwork() })
        }
    }

    /**
     * Retrying to initialize network state monitoring.
     */
    fun retryInitializingNetworkStatusListener() {
        Timber.d("Will retry initialization of network state monitoring after $TIMEOUT_RETRY_INITIALIZING_NETWORK_LISTENER ms")
        Handler().postDelayed({ this.initializeNetworkStatusListener() }, TIMEOUT_RETRY_INITIALIZING_NETWORK_LISTENER)
    }

    /**
     * Setting if the device is connected to the Internet.
     *
     * @param connectedToInternet Is the device now connected to the Internet?
     * @param networkType         Current Network Type in form of ConnectivityManager.TYPE_WIFI types.
     */
    fun setConnectedToInternet(connectedToInternet: Boolean, networkType: Int) {
        synchronized(mConnectedLock) {
            Timber.d("Setting if the device is connected to the Internet status to: $connectedToInternet")
            mIsConnectedToInternet = connectedToInternet
            this.networkType = networkType
            mIsInternetAvailableObservable.onNext(connectedToInternet)
        }
    }

    fun displayErrorInternal(context: Context, msg: String) {
        Timber.d("Trying to show Alert dialog")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok") { dialog, id -> dialog.cancel() }
        val alert = builder.create()

        alert.show()
    }

    companion object {

        /**
         * Returns current network connectivity status.
         *
         * @param context
         * @return
         */
        fun getConnectivityStatus(context: Context): NetworkType {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (activeNetwork != null) {
                when (activeNetwork.type) {
                    ConnectivityManager.TYPE_WIFI -> return NetworkType.WIFI
                    ConnectivityManager.TYPE_MOBILE -> return NetworkType.MOBILE
                }
            }
            return NetworkType.NOT_CONNECTED
        }

        /**
         * Tells if the device is connected to some network.
         *
         * @param context
         * @return
         */
        fun isConnected(context: Context): Boolean {
            return getConnectivityStatus(context) != NetworkType.NOT_CONNECTED
        }
    }
}
