package com.playlistappkotlin.ui.home

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import butterknife.BindString
import butterknife.BindView
import butterknife.OnClick
import com.playlistappkotlin.R
import com.playlistappkotlin.eventbus.event.OpenWebViewEvent
import com.playlistappkotlin.eventbus.event.SetMainFragmentDetailsEvent
import com.playlistappkotlin.ext.FragmentUtils.ABOUT_POSITION
import com.playlistappkotlin.ext.FragmentUtils.FAVORITES_POSITION
import com.playlistappkotlin.ext.FragmentUtils.TRACKS_POSITION
import com.playlistappkotlin.ui.base.BaseActivity
import com.playlistappkotlin.ui.home.about.AboutFragment
import com.playlistappkotlin.ui.home.settings.SettingsFragment
import com.playlistappkotlin.ui.home.tracks.TracksFragment
import com.squareup.otto.Subscribe
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeMvpView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: HomeMvpPresenter

    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.nav_view)
    lateinit var mNavigationView: NavigationView
    @BindView(R.id.layout_container)
    lateinit var mDrawer: DrawerLayout
    @BindView(R.id.btnFilter)
    lateinit var mBtnFilter: ImageButton

    @BindString(R.string.menu_tracks)
    lateinit var mMenuTracks: String
    @BindString(R.string.menu_favorites)
    lateinit var mMenuFavorites: String
    @BindString(R.string.menu_tools)
    lateinit var mMenuTools: String
    @BindString(R.string.menu_about)
    lateinit var mMenuAbout: String
    @BindString(R.string.menu_share)
    lateinit var mMenuShare: String

    override fun attachLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initInjector() {
        Timber.d("Injecting \"Home\" activity")
        activityComponent.inject(this)
    }

    override fun initViews() {
        Timber.d("Initializing view elements")
        presenter.onAttach(this@HomeActivity)
    }

    override fun prepareLayout() {
        super.prepareLayout()
        prepareToolbar()
    }

    private fun prepareToolbar() {
        setSupportActionBar(mToolbar)
        prepareNavigationDrawer()
    }

    /**
     * Prepares Navigation drawer with items.
     */
    private fun prepareNavigationDrawer() {
        Timber.d("Preparing Navigation view and drawer")
        val mDrawerToggle = object : ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
            }
        }
        mDrawer.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        mNavigationView.setNavigationItemSelectedListener(this)
        presenter.onNavMenuCreated()
    }

    @Subscribe
    fun onSetMainFragmentDetailsEvent(event: SetMainFragmentDetailsEvent) {
        Timber.d("Setting main fragment appearance details " + event.fragmentPosition)
        setToolbarItems(event.fragmentPosition)
        setSelectedMenuItem(event.menuItem)
    }

    /**
     * Setting menu items based on selected menu position.
     */
    private fun setSelectedMenuItem(position: Int) {
        Timber.d("Set selected menu item based on last selected position $position")
        mNavigationView.setCheckedItem(position)
    }

    /**
     * Setting toolbar items based on fragment position.
     */
    private fun setToolbarItems(position: Int) {
        Timber.d("Set toolbar items based on last selected fragment with position $position")
        when (position) {
            TRACKS_POSITION -> {
                setToolbarTitle(mMenuTracks)
                prepareFilterIcon(true)
            }
            FAVORITES_POSITION -> {
                setToolbarTitle(mMenuFavorites)
                prepareFilterIcon(false)
            }
            ABOUT_POSITION -> {
                setToolbarTitle(mMenuAbout)
                prepareFilterIcon(false)
            }
            else -> {
            }
        }
    }

    private fun setToolbarTitle(title: String?) {
        mToolbar.title = title
    }

    private fun prepareFilterIcon(show: Boolean) {
        mBtnFilter.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START)
        } else {
            val fragmentManager = supportFragmentManager
            val fragment = fragmentManager.findFragmentByTag(SettingsFragment.TAG)
            fragment?.let {
                onFragmentDetached(SettingsFragment.TAG)
            } ?: run {
                super.onBackPressed()
            }
        }
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        fragment?.let {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow()
            unlockDrawer()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_tracks -> showTracksFragment()
            R.id.nav_favorites -> showFavoritesFragment()
            R.id.nav_tools -> showSettingsFragment()
            R.id.nav_about -> showAboutFragment()
            R.id.nav_share -> { // TODO: Should open share dialog fragment
            }
            else -> {
            }
        }
        mDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showFavoritesFragment() {
        Timber.d("Showing \"Favorites\" fragment")
//        supportFragmentManager
//                .beginTransaction()
//                .addToBackStack(FavoritesFragment.TAG)
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .replace(R.id.layoutMainContainer, FavoritesFragment.newInstance(R.id.nav_favorites), FavoritesFragment.TAG)
//                .commit()
    }

    override fun showAboutFragment() {
        Timber.d("Showing \"About\" fragment")
        supportFragmentManager
                .beginTransaction()
                .addToBackStack(TracksFragment.TAG)
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .replace(R.id.layoutMainContainer, AboutFragment.newInstance(R.id.nav_about), AboutFragment.TAG)
                .commit()
    }

    override fun showSettingsFragment() {
        Timber.d("Showing \"Settings\" fragment")
        lockDrawer()
        supportFragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.layout_container, SettingsFragment.newInstance(R.id.nav_tools), SettingsFragment.TAG)
                .commit()
    }

    override fun showTracksFragment() {
        Timber.d("Showing \"Tracks\" fragment")
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(TracksFragment.TAG)
        if (fragment == null) {
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .replace(R.id.layoutMainContainer, TracksFragment.newInstance(R.id.nav_tracks), TracksFragment.TAG)
                    .commit()
        } else {
            fragmentManager
                    .beginTransaction()
                    .addToBackStack(TracksFragment.TAG)
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .replace(R.id.layoutMainContainer, TracksFragment.newInstance(R.id.nav_tracks))
                    .commit()
        }
    }

    @OnClick(R.id.btnFilter)
    fun onFilterClicked() {
        showSettingsFragment()
    }

    @Subscribe
    fun onOpenWebViewEvent(event: OpenWebViewEvent) {
//        Timber.d("Trying to open \"Web view\" activity " + event.getWebUrl())
//        val intent = Intent(this, WebViewActivity::class.java)
//        intent.putExtra(EXTRA_WEB_URL, event.getWebUrl())
//        intent.putExtra(EXTRA_WEB_TITLE, event.getWebTitle())
//        startActivity(intent)
    }

    override fun lockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onResume() {
        super.onResume()
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }
}
