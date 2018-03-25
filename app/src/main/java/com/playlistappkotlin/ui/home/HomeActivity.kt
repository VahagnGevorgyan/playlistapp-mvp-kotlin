package com.playlistappkotlin.ui.home

import android.content.Intent
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
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_WEB_TITLE
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_WEB_URL
import com.playlistappkotlin.ext.FragmentUtils.ABOUT_POSITION
import com.playlistappkotlin.ext.FragmentUtils.DEFAULT_POSITION
import com.playlistappkotlin.ext.FragmentUtils.FAVORITES_POSITION
import com.playlistappkotlin.ext.FragmentUtils.TRACKS_POSITION
import com.playlistappkotlin.ui.base.BaseActivity
import com.playlistappkotlin.ui.web.WebViewActivity
import com.squareup.otto.Subscribe
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeMvpView, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var mPresenter: HomeMvpPresenter<HomeMvpView>

    @BindView(R.id.toolbar)
    internal var mToolbar: Toolbar? = null
    @BindView(R.id.nav_view)
    internal var mNavigationView: NavigationView? = null
    @BindView(R.id.layout_container)
    internal var mDrawer: DrawerLayout? = null
    @BindView(R.id.btnFilter)
    internal var mBtnFilter: ImageButton? = null

    @BindString(R.string.menu_tracks)
    private var mMenuTracks: String? = null
    @BindString(R.string.menu_favorites)
    private var mMenuFavorites: String? = null
    @BindString(R.string.menu_tools)
    internal var mMenuTools: String? = null
    @BindString(R.string.menu_about)
    private var mMenuAbout: String? = null
    @BindString(R.string.menu_share)
    internal var mMenuShare: String? = null

    override fun attachLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initInjector() {
        Timber.d("Injecting \"Home\" activity")
        activityComponent?.inject(this)
    }

    override fun initViews() {
        Timber.d("Trying to initialize view elements")
        mPresenter.onAttach(this@HomeActivity)
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
                drawerView.let { super.onDrawerOpened(it) }
                hideKeyboard()
            }
        }
        mDrawer?.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        mNavigationView?.setNavigationItemSelectedListener(this)
        mPresenter.onNavMenuCreated()
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
        mNavigationView?.setCheckedItem(position)
    }

    /**
     * Setting toolbar items based on fragment position.
     */
    fun setToolbarItems(position: Int) {
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
            DEFAULT_POSITION -> {
            }
            else -> {
            }
        }
    }

    /**
     * Setting toolbar title.
     * @param title
     */
    fun setToolbarTitle(title: String?) {
        mToolbar?.title = title
    }

    fun prepareFilterIcon(show: Boolean) {
        mBtnFilter?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
//        if (mDrawer!!.isDrawerOpen(GravityCompat.START)) {
//            mDrawer!!.closeDrawer(GravityCompat.START)
//        } else {
//            val fragmentManager = supportFragmentManager
//            val fragment = fragmentManager.findFragmentByTag(SettingsFragment.TAG)
//            if (fragment == null) {
//                super.onBackPressed()
//            } else {
//                onFragmentDetached(SettingsFragment.TAG)
//            }
//        }
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        fragment?.let {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(it)
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
            R.id.nav_share -> {
            }
            else -> {
            }
        }
        mDrawer!!.closeDrawer(GravityCompat.START)
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
//        supportFragmentManager
//                .beginTransaction()
//                .addToBackStack(TracksFragment.TAG)
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .replace(R.id.layoutMainContainer, AboutFragment.newInstance(R.id.nav_about), AboutFragment.TAG)
//                .commit()
    }

    override fun showSettingsFragment() {
        Timber.d("Showing \"Settings\" fragment")
        lockDrawer()
//        supportFragmentManager
//                .beginTransaction()
//                .disallowAddToBackStack()
//                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                .add(R.id.layout_container, SettingsFragment.newInstance(R.id.nav_tools), SettingsFragment.TAG)
//                .commit()
    }

    override fun showTracksFragment() {
        Timber.d("Showing \"Tracks\" fragment")
        val fragmentManager = supportFragmentManager
//        val fragment = fragmentManager.findFragmentByTag(TracksFragment.TAG)
//        if (fragment == null) {
//            supportFragmentManager
//                    .beginTransaction()
//                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                    .replace(R.id.layoutMainContainer, TracksFragment.newInstance(R.id.nav_tracks), TracksFragment.TAG)
//                    .commit()
//        } else {
//            supportFragmentManager
//                    .beginTransaction()
//                    .addToBackStack(TracksFragment.TAG)
//                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                    .replace(R.id.layoutMainContainer, TracksFragment.newInstance(R.id.nav_tracks))
//                    .commit()
//        }
    }

    @OnClick(R.id.btnFilter)
    fun onFilterClicked() {
        showSettingsFragment()
    }

    @Subscribe
    fun onOpenWebViewEvent(event: OpenWebViewEvent) {
        Timber.d("Trying to open \"Web view\" activity ${event.webUrl}")
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra(EXTRA_WEB_URL, event.webUrl)
        intent.putExtra(EXTRA_WEB_TITLE, event.webTitle)
        startActivity(intent)
    }

    override fun lockDrawer() {
        mDrawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
        mDrawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onResume() {
        super.onResume()
        mDrawer?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }
}
