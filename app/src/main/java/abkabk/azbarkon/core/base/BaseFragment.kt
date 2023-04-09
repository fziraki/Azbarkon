package abkabk.azbarkon.core.base

import abkabk.azbarkon.core.extension.showLoadingDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId){

    private lateinit var myActivity: BaseActivity
    protected open var bottomNavigationViewVisibility = View.GONE
    private var loadingDialog: AlertDialog? = null

    fun showLoading(){
        loadingDialog?.dismiss()
        loadingDialog = this.showLoadingDialog()
    }

    fun hideLoading(){
        loadingDialog?.dismiss()
    }

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



}