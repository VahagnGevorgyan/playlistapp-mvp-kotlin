package com.playlistappkotlin.ui.home.tracks

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.playlistappkotlin.R
import com.playlistappkotlin.data.network.data.track.TrackItem
import com.playlistappkotlin.eventbus.event.FavoriteClickedEvent
import com.playlistappkotlin.eventbus.event.RefreshTracksEvent
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_FRAGMENT_POSITION
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_MENU_ITEM_ID
import com.playlistappkotlin.ext.FragmentUtils.TRACKS_POSITION
import com.playlistappkotlin.ui.adapter.TrackListAdapter
import com.playlistappkotlin.ui.home.HomeBaseFragment
import com.squareup.otto.Subscribe
import timber.log.Timber
import javax.inject.Inject

class TracksFragment : HomeBaseFragment(), TracksMvpView {

    @Inject
    lateinit var mPresenter: TracksMvpPresenter

    @Inject
    lateinit var mAdapter: TrackListAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @BindView(R.id.recyclerViewTracks)
    lateinit var mRecyclerViewTracks: RecyclerView
    @BindView(R.id.tracksPullToRefresh)
    lateinit var mTracksPullToRefresh: SwipeRefreshLayout

    internal var mIsLoading = false

    override val rootViewId: Int
        get() = R.layout.fragment_tracks

    override fun initInjector(view: View) {
        Timber.d("Injecting \"Tracks\" fragment")
        activityComponent?.inject(this)
        setUnBinder(ButterKnife.bind(this, view))
        mPresenter.onAttach(this)
    }

    override fun prepareView(rootView: View) {
        Timber.d("Preparing fragment elements")
        prepareTracksAdapter()
        prepareSwipeLayout()
        mPresenter.loadTrackItems()
    }

    /**
     * Prepares Pull to refresh listener.
     */
    private fun prepareSwipeLayout() {
        Timber.d("Preparing Pull to refresh layout listeners")
        mTracksPullToRefresh.setOnRefreshListener {
            Timber.d("Trying to refresh order items list")
            mPresenter.loadTrackItems()
        }
    }

    /**
     * Prepares Tracks list view adapter.
     */
    private fun prepareTracksAdapter() {
        Timber.d("Preparing \"Tracks\" list view adapter")
        mRecyclerViewTracks.layoutManager = mLayoutManager
        mRecyclerViewTracks.adapter = mAdapter
        mRecyclerViewTracks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    val totalItemCount = mLayoutManager.itemCount
                    val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()

                    if (!mIsLoading && totalItemCount <= lastVisibleItem + 5) {
                        mPresenter.nextTrackItems()
                        mIsLoading = true
                    }
                }
            }
        })
    }

    override fun updateTracks(trackItems: List<TrackItem>?) {
        Timber.d("Updating track list items $trackItems")
        mTracksPullToRefresh.post { mTracksPullToRefresh.isRefreshing = false }
        trackItems?.let { mAdapter.updateTrackList(it) }
        mAdapter.notifyDataSetChanged()
    }

    override fun addTracks(trackItems: List<TrackItem>?) {
        Timber.d("Adding new items to track list items $trackItems")
        trackItems?.let { mAdapter.addTrackList(it) }
        mAdapter.notifyDataSetChanged()
        mIsLoading = false
    }

    override fun trackItemUpdated(item: TrackItem, position: Int) {
        Timber.d("Track item is updated ${item.isFavorite}")
        item.isFavorite = !item.isFavorite
        mAdapter.updateTrackItem(item, position)
        mAdapter.notifyDataSetChanged()
    }

    @Subscribe
    fun onRefreshTracksEvent(event: RefreshTracksEvent) {
        Timber.d("Trying to refresh track list items")
        mPresenter.loadTrackItems()
    }

    @Subscribe
    fun onFavoriteClickedEvent(event: FavoriteClickedEvent) {
        Timber.d("Favorite item is clicked ${event.item}")
        mPresenter.setFavoriteItem(event.item, event.position)
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

    companion object {

        val TAG = TracksFragment::class.java.simpleName

        fun newInstance(id: Int): TracksFragment {
            val args = Bundle()
            args.putInt(EXTRA_FRAGMENT_POSITION, TRACKS_POSITION)
            args.putInt(EXTRA_MENU_ITEM_ID, id)
            val fragment = TracksFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
