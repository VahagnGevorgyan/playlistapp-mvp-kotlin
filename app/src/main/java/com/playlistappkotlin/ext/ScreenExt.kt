package com.playlistappkotlin.ext

import android.content.res.Resources

/**
 * Method for getting dp by pixel
 * @param dp - Dpi
 * @return Pixel
 */
fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

/**
 * Method for getting px by dp
 * @param px - Pixel
 * @return Dpi
 */
fun pxToDp(px: Int): Int {
    return (px / Resources.getSystem().displayMetrics.density).toInt()
}