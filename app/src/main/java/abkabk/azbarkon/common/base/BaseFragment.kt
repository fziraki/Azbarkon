package abkabk.azbarkon.common.base

import abkabk.azbarkon.common.extension.showLoadingDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), BackStackHandler{

    private lateinit var myActivity: BaseActivity
    protected open var bottomNavigationViewVisibility = View.GONE
    private var loadingDialog: AlertDialog? = null
    protected open var defaultHandleBackStack = true
    protected open var toolbarVisibility = View.VISIBLE

    fun showLoading(){
        loadingDialog?.dismiss()
        loadingDialog = this.showLoadingDialog()
    }

    fun hideLoading(){
        loadingDialog?.dismiss()
    }

    fun initTitle(title: String, backVisibility: Int, goHomeVisibility: Int) {
        myActivity.setToolbarTitle(title, backVisibility, goHomeVisibility)
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

        if (toolbarVisibility == View.VISIBLE) {
            myActivity.showToolbar()
        } else {
            myActivity.hideToolbar()
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
        if (defaultHandleBackStack){
            findNavController().popBackStack()
        }
    }

}