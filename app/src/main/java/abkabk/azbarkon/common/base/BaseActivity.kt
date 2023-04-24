package abkabk.azbarkon.common.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    abstract fun showBottomNavigation()
    abstract fun hideBottomNavigation()
    abstract fun showToolbar()
    abstract fun hideToolbar()
    abstract fun setToolbarTitle(title: String, backVisibility: Int)

}