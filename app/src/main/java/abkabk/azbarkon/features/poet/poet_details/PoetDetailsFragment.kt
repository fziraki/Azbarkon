package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.R
import abkabk.azbarkon.core.SpaceItemDecoration
import abkabk.azbarkon.core.base.BaseFragment
import abkabk.azbarkon.core.extension.autoCleared
import abkabk.azbarkon.core.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentPoetDetailsBinding
import abkabk.azbarkon.features.poet.domain.Children
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PoetDetailsFragment: BaseFragment(R.layout.fragment_poet_details) {

    override var defaultHandleBackStack = false

    private val viewBinding by viewBinding(FragmentPoetDetailsBinding::bind)
    private val viewModel: PoetDetailsViewModel by viewModels()

    private var childrenAdapter by autoCleared<ChildrenAdapter>()
    private val childItemClickListener = { child: Children ->
        viewModel.getCategories(child.id)
    }

    override fun setupScreen() {

    }

    override fun setupObservers() {

        viewModel.eventFlow
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is PoetDetailsViewModel.UiEvent.ShowSnackbar -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is PoetDetailsViewModel.UiEvent.Navigate -> {
                        findNavController().navigate(it.action, it.bundle)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        initRecyclerView()

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { poetDetailsState ->

                if (poetDetailsState.isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }

                poetDetailsState.poet?.let {
                    Glide
                        .with(requireContext())
                        .load(it.loadableImageUrl)
                        .into(viewBinding.poetImg)

                    viewBinding.poetName.text = it.name
                }

                poetDetailsState.children?.let { children ->
                    childrenAdapter.setData(children)
                }

                if (poetDetailsState.children?.isEmpty() == true){
                    viewModel.shouldNavigate(
                        R.id.action_poetDetailsFragment_to_poemList,
                        bundleOf("catId" to poetDetailsState.catId)
                    )
                }

                activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (poetDetailsState.poet?.rootCatId == poetDetailsState.catId){
                            findNavController().popBackStack()
                        }else{
                            viewModel.restore()
                        }
                    }
                })

            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun initRecyclerView() {
        childrenAdapter = ChildrenAdapter(childItemClickListener)
        with(viewBinding.childrenRv) {
            adapter = childrenAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            addItemDecoration(SpaceItemDecoration(10))
        }
    }

}