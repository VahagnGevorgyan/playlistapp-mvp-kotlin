package com.playlistappkotlin.ui.view

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.playlistappkotlin.ext.Constants.Companion.CUSTOM_ATTR_SCHEMAS
import com.playlistappkotlin.ext.Constants.Companion.DEFAULT_FONT
import com.playlistappkotlin.ext.Typefaces

class CustomTextView : AppCompatTextView {

    private var mContext: Context
    private var fontName: String? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        for (i in 0 until attrs.attributeCount) {
            this.fontName = attrs.getAttributeValue(CUSTOM_ATTR_SCHEMAS, "fonts")
            initialize()
        }
    }

    private fun initialize() {
        if (null == this.fontName) {
            this.fontName = DEFAULT_FONT
        }
        if (!isInEditMode) {
            val font = Typefaces.get(this.mContext, this.fontName)
            font?.let { typeface = it }
        }
    }

    override fun setTypeface(tf: Typeface) {
        super.setTypeface(tf)
    }

    override fun setTextColor(color: Int) {
        super.setTextColor(color)
    }

}