package com.playlistappkotlin.ui.adapter

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.playlistappkotlin.R
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.eventbus.SingletonBus
import com.playlistappkotlin.eventbus.event.FavoriteClickedEvent
import com.playlistappkotlin.eventbus.event.OpenWebViewEvent
import timber.log.Timber
import java.util.ArrayList

class TrackListAdapter(private val mContext: AppCompatActivity) : RecyclerView.Adapter<TrackListAdapter.TrackViewHolder>() {

    private var mList: MutableList<TrackItem> = ArrayList()

    fun updateTrackList(list: List<TrackItem>) {
        Timber.d("Updating TrackItem list items $list")
        mList.clear()
        mList.addAll(list)
    }

    fun addTrackList(list: List<TrackItem>) {
        Timber.d("Adding TrackItem list items $list")
        mList.addAll(list)
    }

    fun updateTrackItem(item: TrackItem, position: Int) {
        Timber.d("Updating TrackItem in position $position")
        mList[position] = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_track, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {

        val item = mList[position]

        holder.textViewName.text = item.name
        holder.textViewArtist.text = item.artist?.name
        if (!item.duration.isNullOrEmpty() && item.duration != "0") {
            holder.textViewDuration.text = mContext.getString(R.string.str_track_time, item.duration)
        }

        holder.btnFavorite.background = ContextCompat.getDrawable(mContext, if (item.isFavorite) android.R.drawable.star_big_on else android.R.drawable.star_big_off)

        item.imageList?.let {
            for (img in it) {
                if (img.size == "large") {
                    Glide
                        .with(mContext)
                        .load(img.text)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                                return false
                            }

                            override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                                holder.loadingBar.visibility = View.INVISIBLE
                                return false
                            }
                        })
                        .into(holder.imageViewTrack)
                    break
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.textViewName)
        lateinit var textViewName: TextView
        @BindView(R.id.imageViewTrack)
        lateinit var imageViewTrack: ImageView
        @BindView(R.id.textViewDuration)
        lateinit var textViewDuration: TextView
        @BindView(R.id.textViewArtist)
        lateinit var textViewArtist: TextView
        @BindView(R.id.loadingBar)
        lateinit var loadingBar: ProgressBar
        @BindView(R.id.btnFavorite)
        lateinit var btnFavorite: ImageButton

        init {
            ButterKnife.bind(this, view)
        }

        @OnClick(R.id.itemLayout)
        fun onItemClicked() {
            SingletonBus.instance.post(
                    OpenWebViewEvent(mList[adapterPosition].url, mList[adapterPosition].name))
        }

        @OnClick(R.id.btnFavorite)
        fun onFavoriteClicked() {
            SingletonBus.instance.post(
                    FavoriteClickedEvent(mList[adapterPosition], adapterPosition))
        }
    }
}

