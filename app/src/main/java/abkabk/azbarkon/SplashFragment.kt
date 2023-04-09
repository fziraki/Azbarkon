package abkabk.azbarkon

import abkabk.azbarkon.core.base.BaseFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment: BaseFragment(R.layout.fragment_splash) {

    override fun setupScreen() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            delay(3000)
            findNavController().navigate(R.id.action_splashFragment_to_poetNavGraph)
        }
    }

    override fun setupObservers() {
    }

}