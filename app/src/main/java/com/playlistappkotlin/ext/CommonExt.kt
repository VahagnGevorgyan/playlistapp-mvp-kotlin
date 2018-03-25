package com.playlistappkotlin.ext

import android.app.Activity
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.playlistappkotlin.R
import com.playlistappkotlin.ext.Constants.Companion.EMAIL_PATTERN
import java.util.regex.Pattern

fun showLoadingDialog(context: Context): ProgressDialog {
    val progressDialog = ProgressDialog(context)
    progressDialog.show()
    progressDialog.window?.let {
        it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
    }
    return progressDialog
}

/**
 * Method for checking email validation.
 */
fun validateEmail(email: CharSequence): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Method for checking email validation with custom pattern.
 */
fun validateEmailPattern(email: CharSequence): Boolean {
    val pattern = Pattern.compile(EMAIL_PATTERN)
    return pattern.matcher(email).matches()
}

/**
 * Method for checking password validation.
 */
fun validatePassword(password: String): Boolean {
    return password.length > 5
}

/**
 * Method for checking password confirmation.
 */
fun validatePasswordConfirmation(password: String, confirmPwd: String): Boolean {
    return password == confirmPwd
}

/**
 * Method for showing toast.
 */
fun toast(context: Activity, toastText: String) {

    val inflater = LayoutInflater.from(context)
    val toastView = inflater.inflate(R.layout.layout_toast, context.findViewById(R.id.layoutToast), false)

    val textViewToast: TextView = toastView.findViewById(R.id.textViewToast)
    textViewToast.text = toastText

    val toast = Toast(context)
    toast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM, 0, dpToPx(64))
    toast.duration = Toast.LENGTH_SHORT
    toast.view = toastView
    toast.show()
}

/**
 * Method for phone calling
 * @param context - Context
 * @param phoneNumber - Phone number
 */
fun call(context: Context, phoneNumber: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel: $phoneNumber")
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        Toast.makeText(context, "There are no phone clients installed.", Toast.LENGTH_SHORT).show()
    }

}