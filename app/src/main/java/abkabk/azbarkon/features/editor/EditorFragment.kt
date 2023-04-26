package abkabk.azbarkon.features.editor

import abkabk.azbarkon.R
import abkabk.azbarkon.common.base.BaseFragment
import android.view.View

class EditorFragment : BaseFragment(R.layout.fragment_editor) {

    override var bottomNavigationViewVisibility = View.VISIBLE

    override fun setupScreen() {

        initTitle(getString(R.string.poetry_pic), View.GONE, View.GONE)
    }

    override fun setupObservers() {
    }
}