package com.playlistappkotlin.ui.home.about

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.playlistappkotlin.BuildConfig
import com.playlistappkotlin.R
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_FRAGMENT_POSITION
import com.playlistappkotlin.ext.Constants.Companion.EXTRA_MENU_ITEM_ID
import com.playlistappkotlin.ext.FragmentUtils.ABOUT_POSITION
import com.playlistappkotlin.ui.home.HomeBaseFragment
import timber.log.Timber
import javax.inject.Inject

class AboutFragment : HomeBaseFragment(), AboutMvpView {

    @Inject
    lateinit var presenter: AboutMvpPresenter

    @BindView(R.id.version_number)
    lateinit var versionNumberView: TextView

    override val rootViewId: Int
        get() = R.layout.fragment_about

    override fun initInjector(view: View) {
        Timber.d("Injecting \"About\" fragment")

        activityComponent?.inject(this)
        setUnBinder(ButterKnife.bind(this, view))
        presenter.onAttach(this)
    }

    override fun prepareView(rootView: View) {
        Timber.d("Preparing fragment elements")

        versionNumberView.text = getString(R.string.about_version_number, BuildConfig.VERSION_NAME)
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    companion object {

        val TAG: String = AboutFragment::class.java.simpleName

        fun newInstance(id: Int): AboutFragment {
            val args = Bundle()
            args.putInt(EXTRA_FRAGMENT_POSITION, ABOUT_POSITION)
            args.putInt(EXTRA_MENU_ITEM_ID, id)
            val fragment = AboutFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
