package abkabk.azbarkon.features.likes

import abkabk.azbarkon.R
import abkabk.azbarkon.common.Constants
import abkabk.azbarkon.common.SpaceItemDecoration
import abkabk.azbarkon.common.base.BaseFragment
import abkabk.azbarkon.common.extension.autoCleared
import abkabk.azbarkon.common.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentLikesBinding
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LikesFragment: BaseFragment(R.layout.fragment_likes) {

    override var bottomNavigationViewVisibility = View.VISIBLE

    private val viewBinding by viewBinding(FragmentLikesBinding::bind)
    private val viewModel: LikesViewModel by viewModels()

    private var likedPoemsAdapter by autoCleared<LikedPoemsAdapter>()

    private val poemItemClickListener = { poem: PoemDetailsUi ->
        findNavController().navigate(
                R.id.action_likes_to_poemDetailsFragment,
                bundleOf(Constants.POEM to poem)
            )
    }

    override fun setupScreen() {
        initTitle(getString(R.string.likes), View.GONE, View.GONE)
        initRecyclerView()
        viewModel.onUiEvent(LikesViewModel.UiEvent.OnSetupScreen)
    }

    override fun setupObservers() {

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                if (it.isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }

                it.poemList?.let { poems ->
                    likedPoemsAdapter.setData(poems)
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.uiEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiEvent ->
                when(uiEvent){
                    is LikesViewModel.UiEvent.ShowSnackbar -> {
                        Toast.makeText(requireContext(), uiEvent.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initRecyclerView() {
        likedPoemsAdapter = LikedPoemsAdapter(poemItemClickListener)
        with(viewBinding.likedPoemsRv) {
            adapter = likedPoemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            addItemDecoration(SpaceItemDecoration(10))
        }
    }
}