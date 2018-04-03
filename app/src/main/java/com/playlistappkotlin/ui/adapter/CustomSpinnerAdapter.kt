package com.playlistappkotlin.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.playlistappkotlin.R

class CustomSpinnerAdapter(private var mContext: Context, resource: Int, private val mList: MutableList<String>?) : ArrayAdapter<String>(mContext, resource, mList) {

    fun updateItems(list: List<String>) {
        mList!!.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v = super.getView(position, convertView, parent)
        if (mList != null && position == 0) {
            (v as TextView).setTextColor(ContextCompat.getColor(mContext, R.color.grey_dark))
        } else {
            (v as TextView).setTextColor(ContextCompat.getColor(mContext, R.color.black))
        }
        return v
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val v: View
        if (position == 0) {
            val tv = View(context)
            tv.visibility = View.GONE
            v = tv
        } else {
            v = super.getDropDownView(position, null, parent)
        }
        return v
    }
}