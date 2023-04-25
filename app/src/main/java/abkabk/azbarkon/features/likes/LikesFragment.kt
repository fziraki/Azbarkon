package abkabk.azbarkon.features.likes

import abkabk.azbarkon.R
import abkabk.azbarkon.common.base.BaseFragment
import android.view.View

class LikesFragment: BaseFragment(R.layout.fragment_likes) {

    override var bottomNavigationViewVisibility = View.VISIBLE

    override fun setupScreen() {

        initTitle(getString(R.string.likes), View.GONE)
    }

    override fun setupObservers() {
    }
}