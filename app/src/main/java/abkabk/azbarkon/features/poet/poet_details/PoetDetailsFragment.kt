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

    private val viewBinding by viewBinding(FragmentPoetDetailsBinding::bind)
    private val viewModel: PoetDetailsViewModel by viewModels()

    private var childrenAdapter by autoCleared<ChildrenAdapter>()
    private val childItemClickListener = { child: Children ->
        viewModel.getCategories(child.id)
    }

    override fun setupScreen() {

    }

    override fun setupObservers() {

        initRecyclerView()

        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { poetDetailsState ->

                if (poetDetailsState.isLoading){
                    showLoading()
                }else{
                    hideLoading()
                }

                Glide
                    .with(requireContext())
                    .load(poetDetailsState.poetImage)
                    .into(viewBinding.poetImg)

                viewBinding.poetName.text = poetDetailsState.poetName

                poetDetailsState.children?.let { children ->
                    childrenAdapter.setData(children)
                }

                activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        poetDetailsState.catId?.let {
                            viewModel.getCategories(it)
                        }?:run {
                            findNavController().popBackStack()
                        }
                    }
                })

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.uiEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
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