package abkabk.azbarkon.core.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(){

    abstract fun showBottomNavigation()
    abstract fun hideBottomNavigation()

}