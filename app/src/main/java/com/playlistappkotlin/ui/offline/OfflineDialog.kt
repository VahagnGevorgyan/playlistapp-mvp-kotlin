package com.playlistappkotlin.ui.offline

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.playlistappkotlin.R
import com.playlistappkotlin.ui.view.PopupActionButton
import timber.log.Timber

/**
 * Shows when network state was lost.
 */
class OfflineDialog
/**
 * Setting up OfflineDialog popup with retry button.
 *
 */
private constructor(private val mActivity: AppCompatActivity, private val mRetryRunnable: Runnable?) {

    @BindView(R.id.popup_buttons_container)
    lateinit var mButtonsContainer: LinearLayout

    @BindString(R.string.popup_offline_retry)
    lateinit var mButtonLabel: String
    private var mAlertDialog: AlertDialog? = null
    private var mUnBinder: Unbinder? = null

    init {
        Timber.d("Setting up \"OfflineDialog\" popup")

        validatePopupInputs()
        createDialog()
    }

    /**
     * Showing popup.
     */
    fun show() {
        Timber.d("Showing popup")
        mAlertDialog!!.show()
    }

    /**
     * Creating popup.
     */
    private fun createDialog() {
        Timber.d("Creating Green Popup")

        val builder = AlertDialog.Builder(mActivity)

        val rootView = getRootView(LayoutInflater.from(mActivity))
        builder.setView(rootView)

        mAlertDialog = builder.create()
        mAlertDialog?.setCanceledOnTouchOutside(false)

        customizeOfflineDialog()
        isShown = true
    }

    /**
     * Layout for popup.
     */
    private fun getRootView(inflater: LayoutInflater): View {
        Timber.d("Inflating Popup view and binding variables to it")
        val view: View = inflater.inflate(R.layout.dialog_offline, null, false)
        mUnBinder = ButterKnife.bind(this, view)
        return view
    }

    /**
     * Validating popup parameters.
     */
    private fun validatePopupInputs() {
        Timber.d("Validating popup parameters")

        if (mRetryRunnable == null) {
            throw NullPointerException("mRetryRunnable is null")
        }
    }

    /**
     * Adding buttons to the dialog.
     */
    private fun customizeOfflineDialog() {
        Timber.d("Adding Retry button to the popup")
        val retryButton = createActionButton(mButtonLabel, mButtonsContainer!!)
        retryButton.setOnClickListener({ v ->
            Timber.d("Pressed Retry button, dismissing popup")
            dismissPopup()
            if (mRetryRunnable != null) {
                Timber.d("Executing Retry Runnable")
                mRetryRunnable.run()
            }
        })
    }

    /**
     * Creates action button for popup.
     */
    private fun createActionButton(buttonTitle: String?, buttonLayout: ViewGroup): PopupActionButton {
        Timber.d("Creating Popup button with title: " + buttonTitle!!)
        val actionButton = PopupActionButton(mActivity)
        actionButton.setTitle(buttonTitle)
        buttonLayout.addView(actionButton, actionButton.getLayoutParams())
        return actionButton
    }

    /**
     * Destroying popup.
     */
    private fun dismissPopup() {
        Timber.d("Destroying popup")
        mAlertDialog!!.dismiss()
        mUnBinder!!.unbind()
        isShown = false
    }

    companion object {

        private var isShown = false

        fun alreadyOnTheScreen(): Boolean {
            return isShown
        }

        /**
         * Showing "No Internet connection" popup.
         * Will Retry operation, or go to the off-line content.
         *
         * @param buttonRetryRunnable What to do to Retry failed operation.
         */
        fun showNoInternetPopup(activity: AppCompatActivity, buttonRetryRunnable: Runnable) {
            Timber.d("Trying to show \"No Internet connection\" popup")

            if (activity.isFinishing) {
                Timber.d("Will not show \"No Internet connection\" popup, because Activity is finishing")
                return
            }

            Timber.d("Will show popup in Activity: " + activity.javaClass.simpleName)

            val noInternetPopup = OfflineDialog(activity,
                    buttonRetryRunnable)

            try {
                Timber.d("Showing \"No Internet connection\" popup")
                noInternetPopup.show()
            } catch (ignore: IllegalStateException) { // NOSONAR
                Timber.d("Not showing popup if saveInstanceState is called")
            }

        }
    }
}