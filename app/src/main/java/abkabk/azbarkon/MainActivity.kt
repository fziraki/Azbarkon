package abkabk.azbarkon

import abkabk.azbarkon.common.base.BaseActivity
import abkabk.azbarkon.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupBottomNavMenu(navController)
    }

    private fun setupBottomNavMenu(navController: NavController){
        viewBinding.bottomNavigation.setupWithNavController(navController)
    }

    override fun showBottomNavigation() {
        viewBinding.bottomNavigation.visibility = View.VISIBLE
        viewBinding.shadow.visibility = View.VISIBLE
    }

    override fun hideBottomNavigation() {
        viewBinding.bottomNavigation.visibility = View.GONE
        viewBinding.shadow.visibility = View.GONE
    }

    override fun showToolbar() {
        viewBinding.toolbar.root.visibility = View.VISIBLE
    }

    override fun hideToolbar() {
        viewBinding.toolbar.root.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String?, backVisibility: Int, goHomeVisibility: Int) {
        title?.let {
            viewBinding.toolbar.title.visibility = View.VISIBLE
            viewBinding.toolbar.title.text = title
        }
        viewBinding.toolbar.back.visibility = backVisibility
        viewBinding.toolbar.goHome.visibility = goHomeVisibility
        viewBinding.toolbar.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        viewBinding.toolbar.goHome.setOnClickListener {
            navController.popBackStack(R.id.poetList,false)
        }
    }

}