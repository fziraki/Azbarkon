package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.common.SpaceItemDecoration
import abkabk.azbarkon.common.base.BaseFragment
import abkabk.azbarkon.common.extension.autoCleared
import abkabk.azbarkon.common.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentPoetListBinding
import abkabk.azbarkon.features.poet.model.PoetUi
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PoetListFragment: BaseFragment(R.layout.fragment_poet_list) {

    override var bottomNavigationViewVisibility = View.VISIBLE
    private val viewBinding by viewBinding(FragmentPoetListBinding::bind)
    private val viewModel: PoetListViewModel by viewModels()
    override var toolbarVisibility = View.GONE

    private var poetsAdapter by autoCleared<PoetsAdapter>()
    var myTracker: SelectionTracker<Long>? = null

    private val poetItemClickListener = { poet: PoetUi ->
        findNavController()
            .navigate(R.id.action_poetList_to_poetDetailsFragment,
                bundleOf("poet" to poet))
    }

    override fun setupScreen() {
        initRecyclerView()
        initSearchView()
        viewBinding.close.setOnClickListener {
            viewBinding.contextMenu.visibility = View.GONE
        }
        viewBinding.pin.setOnClickListener {
            viewModel.pinUnpin()
        }
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
                if (it.poetList.isNotEmpty()){
                    viewBinding.contextMenu.visibility = View.GONE
                    myTracker?.clearSelection()
                    poetsAdapter.setData(it.poetList)
                    delay(300)
                    viewBinding.poetListRv.scrollToPosition(0)
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

        myTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val items = myTracker?.selection!!.size()
                    if (items>0){
                        viewBinding.contextMenu.visibility = View.VISIBLE
                        viewBinding.selectedItemsCount.text = items.toString()
                    }else{
                        viewBinding.contextMenu.visibility = View.GONE
                    }
                    if (myTracker?.hasSelection() == true){
                        viewModel.saveSelectedItemsIds(myTracker!!.selection)
                    }
                }
            })

    }


    private fun initRecyclerView() {
        poetsAdapter = PoetsAdapter(poetItemClickListener)
        with(viewBinding.poetListRv) {
            adapter = poetsAdapter
            layoutManager = StaggeredGridLayoutManager(4,RecyclerView.VERTICAL)
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            addItemDecoration(SpaceItemDecoration(10))
            myTracker = SelectionTracker.Builder(
                "mySelection",
                this,
                MyItemKeyProvider(this),
                MyItemDetailsLookup(this),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }
        poetsAdapter.tracker = myTracker
    }

    private fun initSearchView() {
        viewBinding.searchview.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!viewBinding.searchview.isIconified) {
                    query?.let {
                        poetsAdapter.filter.filter(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!viewBinding.searchview.isIconified){
                    newText?.let {
                        poetsAdapter.filter.filter(it)
                    }
                }
                return true
            }

        })
    }
}