package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.core.SpaceItemDecoration
import abkabk.azbarkon.core.base.BaseFragment
import abkabk.azbarkon.core.extension.autoCleared
import abkabk.azbarkon.core.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentPoetListBinding
import abkabk.azbarkon.features.poet.domain.Poet
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PoetListFragment: BaseFragment(R.layout.fragment_poet_list) {

    override var bottomNavigationViewVisibility = View.VISIBLE
    private val viewBinding by viewBinding(FragmentPoetListBinding::bind)
    private val viewModel: PoetListViewModel by viewModels()

    private var poetsAdapter by autoCleared<PoetsAdapter>()
    private val poetItemClickListener = { poet: Poet ->
        findNavController()
            .navigate(R.id.action_poetList_to_poetDetailsFragment,
                bundleOf("poet" to poet))
    }

    override fun setupScreen() {
        initRecyclerView()
        initSearchView()
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
                poetsAdapter.setData(it.poetList)
                if (it.poetList.isNotEmpty()){
                    viewBinding.searchview.visibility = View.VISIBLE
                }else{
                    viewBinding.searchview.visibility = View.GONE
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.uiEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun initRecyclerView() {
        poetsAdapter = PoetsAdapter(poetItemClickListener)
        with(viewBinding.poetListRv) {
            adapter = poetsAdapter
            layoutManager = StaggeredGridLayoutManager(4,RecyclerView.VERTICAL)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            addItemDecoration(SpaceItemDecoration(10))
        }
    }

    private fun initSearchView() {
        viewBinding.searchview.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    poetsAdapter.filter.filter(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    poetsAdapter.filter.filter(it)
                }
                return true
            }

        })
    }
}