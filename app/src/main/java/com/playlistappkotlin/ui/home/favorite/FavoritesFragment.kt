package com.playlistappkotlin.ui.home.favorite

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
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_FRAGMENT_POSITION
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_MENU_ITEM_ID
import com.playlistappkotlin.ext.FragmentUtils.FAVORITES_POSITION
import com.playlistappkotlin.ui.adapter.TrackListAdapter
import com.playlistappkotlin.ui.home.HomeBaseFragment
import com.squareup.otto.Subscribe
import timber.log.Timber
import javax.inject.Inject

class FavoritesFragment : HomeBaseFragment(), FavoritesMvpView {

    @Inject
    lateinit var mPresenter: FavoritesMvpPresenter

    @Inject
    lateinit var mAdapter: TrackListAdapter

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @BindView(R.id.recyclerViewFavorites)
    lateinit var mRecyclerViewFavorites: RecyclerView
    @BindView(R.id.favoritesPullToRefresh)
    lateinit var mFavoritesPullToRefresh: SwipeRefreshLayout

    internal var mIsLoading = false

    override val rootViewId: Int
        get() = R.layout.fragment_favorite

    override fun initInjector(view: View) {
        Timber.d("Injecting \"Favorites\" fragment")
        activityComponent?.inject(this)
        setUnBinder(ButterKnife.bind(this, view))
        mPresenter.onAttach(this)
    }

    override fun prepareView(rootView: View) {
        Timber.d("Preparing fragment elements")
        prepareFavoritesAdapter()
        prepareSwipeLayout()
        mPresenter.loadFavoriteItems()
    }

    /**
     * Prepares Pull to refresh listener.
     */
    private fun prepareSwipeLayout() {
        Timber.d("Preparing Pull to refresh layout listeners")
        mFavoritesPullToRefresh.setOnRefreshListener {
            Timber.d("Trying to refresh order items list")
            mPresenter.loadFavoriteItems()
        }
    }

    /**
     * Prepares Favorites list view adapter.
     */
    private fun prepareFavoritesAdapter() {
        Timber.d("Preparing \"Favorites\" list view adapter")
        mRecyclerViewFavorites.layoutManager = mLayoutManager
        mRecyclerViewFavorites.adapter = mAdapter
        mRecyclerViewFavorites.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    val totalItemCount = mLayoutManager.itemCount
                    val lastVisibleItem = mLayoutManager.findLastVisibleItemPosition()

                    if (!mIsLoading && totalItemCount <= lastVisibleItem + 5) {
                        mPresenter.nextItems()
                        mIsLoading = true
                    }
                }
            }
        })
    }

    override fun updateItems(items: List<TrackItem>) {
        Timber.d("Updating favorite list items $items")
        mFavoritesPullToRefresh.post { mFavoritesPullToRefresh.isRefreshing = false }
        mAdapter.updateTrackList(items)
        mAdapter.notifyDataSetChanged()
    }

    override fun addItems(items: List<TrackItem>) {
        Timber.d("Adding new items to favorite list items $items")
        mAdapter.addTrackList(items)
        mAdapter.notifyDataSetChanged()
        mIsLoading = false
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

        val TAG: String = FavoritesFragment::class.java.simpleName

        fun newInstance(id: Int): FavoritesFragment {
            val args = Bundle()
            args.putInt(EXTRA_FRAGMENT_POSITION, FAVORITES_POSITION)
            args.putInt(EXTRA_MENU_ITEM_ID, id)
            val fragment = FavoritesFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
