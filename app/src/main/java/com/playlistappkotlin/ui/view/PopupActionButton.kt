package com.playlistappkotlin.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindDimen
import butterknife.BindView
import butterknife.ButterKnife
import com.playlistappkotlin.R

/**
 * Popup action button.
 */
class PopupActionButton : LinearLayout {

    @BindView(R.id.button_title)
    internal var mTitle: TextView? = null
    @BindDimen(R.dimen.navigation_margin)
    internal var mMargin: Int = 0

    init {
        init()
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private fun init() {
        View.inflate(context, R.layout.dialog_popup_button, this)
        ButterKnife.bind(this)
    }

    fun setTitle(title: String) {
        mTitle?.text = title
    }

    /**
     * @return layout params that should be used when adding view to it's parent.
     */
    override fun getLayoutParams(): LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.topMargin = mMargin
        params.rightMargin = mMargin
        params.bottomMargin = mMargin
        return params
    }
}
