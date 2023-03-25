package abkabk.azbarkon.core.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), BackStackHandler{

    private lateinit var myActivity: BaseActivity
    protected open var bottomNavigationViewVisibility = View.GONE

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = (super.getActivity() as BaseActivity)
    }

    override fun onResume() {
        super.onResume()
        if (bottomNavigationViewVisibility == View.VISIBLE) {
            myActivity.showBottomNavigation()
        } else {
            myActivity.hideBottomNavigation()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupScreen()
        setupObservers()
    }


    abstract fun setupScreen()

    abstract fun setupObservers()

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    override fun shouldOverrideBackPressed(): Boolean = true

}