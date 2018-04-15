package com.playlistappkotlin.ext

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.TypedValue
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import timber.log.Timber

/**
 * Returns the pixel height of status bar is such is present.
 */
fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val res = context.resources

    val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = res.getDimensionPixelSize(resourceId)
    }
    return result
}

fun getActionBarHeight(context: Context): Int {
    var actionBarHeight = 0
    val tv = TypedValue()
    if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
    }
    return actionBarHeight
}

/**
 * Getting the width and height of navigation bar. A bit complex chain, but it's the only
 * reliable solution which should work on all of the deivices.
 */
fun getNavigationBarSize(context: Context): Point {
    val appUsableSize = getAppUsableScreenSize(context)
    val realScreenSize = getRealScreenSize(context)

    // navigation bar on the right
    if (appUsableSize.x < realScreenSize.x) {
        return Point(realScreenSize.x - appUsableSize.x, appUsableSize.y)
    }

    // navigation bar at the bottom
    return if (appUsableSize.y < realScreenSize.y) {
        Point(appUsableSize.x, realScreenSize.y - appUsableSize.y)
    } else Point()
}

/**
 * Getting the area reserved for the application.
 */
fun getAppUsableScreenSize(context: Context): Point {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size
}

/**
 * Getting the actual devices screen size.
 */
fun getRealScreenSize(context: Context): Point {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val size = Point()

    if (Build.VERSION.SDK_INT >= 21) {
        display.getRealSize(size)
    } else {
        try {
            size.x = Display::class.java.getMethod("getRawWidth").invoke(display) as Int
            size.y = Display::class.java.getMethod("getRawHeight").invoke(display) as Int
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
        }
    }
    return size
}

fun setWindowUiVisibility(window: Window) {
    var uiFlag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    uiFlag = uiFlag or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.decorView.systemUiVisibility = uiFlag
}
