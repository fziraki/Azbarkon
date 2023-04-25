package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.common.Constants.MY_SELECTION
import abkabk.azbarkon.common.Constants.POET_KEY
import abkabk.azbarkon.common.SpaceItemDecoration
import abkabk.azbarkon.common.base.BaseFragment
import abkabk.azbarkon.common.extension.autoCleared
import abkabk.azbarkon.common.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentPoetListBinding
import abkabk.azbarkon.features.poet.model.PoetUi
import android.annotation.SuppressLint
import android.view.MotionEvent
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
                bundleOf(POET_KEY to poet))
    }

    override fun setupScreen() {
        initRecyclerView()
        initTracker()
        initSearchView()
        viewBinding.close.setOnClickListener {
            viewBinding.contextMenu.visibility = View.GONE
            myTracker?.clearSelection()
        }
        viewBinding.togglePin.setOnClickListener {
            viewModel.onUiEvent(
                PoetListViewModel.UiEvent.TogglePin(
                    shouldPin = viewBinding.togglePin.text.toString() ==
                                resources.getString(R.string.pin)
                )
            )
        }
    }

    override fun setupObservers() {

        viewModel.uiEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { uiEvent ->
                when(uiEvent){
                    is PoetListViewModel.UiEvent.ShowSnackbar -> {
                        Toast.makeText(requireContext(), uiEvent.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.togglePinText
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                it?.let {
                    viewBinding.togglePin.text = it.asString(requireContext())
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

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
                    viewBinding.searchview.setQuery("",true)
                    viewBinding.searchview.onActionViewCollapsed()
                    delay(300)
                    viewBinding.poetListRv.scrollToPosition(0)
                    viewBinding.searchview.visibility = View.VISIBLE
                }else{
                    viewBinding.searchview.visibility = View.GONE
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)


        myTracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()

                    myTracker?.selection?.size()?.let { itemsSize ->
                        if (itemsSize>0){
                            viewBinding.contextMenu.visibility = View.VISIBLE
                            viewBinding.selectedItemsCount.text = itemsSize.toString()
                        }else{
                            viewBinding.contextMenu.visibility = View.GONE
                        }
                    }

                    myTracker?.hasSelection()?.let {
                        viewModel.onUiEvent(
                            PoetListViewModel
                                .UiEvent
                                .OnSelectionChanged(myTracker!!.selection)
                        )
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
        }
    }

    private fun initTracker(){
        with(viewBinding.poetListRv) {
            myTracker = SelectionTracker.Builder(
                MY_SELECTION,
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initSearchView() {
        viewBinding.searchview.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!viewBinding.searchview.isIconified) {
                    query?.let {
                        poetsAdapter.filter.filter(it)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!viewBinding.searchview.isIconified){
                    newText?.let {
                        poetsAdapter.filter.filter(it)
                    }
                }
                return false
            }

        })


        /* note: don't use setOnClickListener because:
        it doesn't trigger after setting isIconified = false
        it only triggers for the first time */
        viewBinding.searchview.setOnTouchListener { _, event ->
            if(event.action == MotionEvent.ACTION_UP){
                viewBinding.searchview.isIconified = false
                true
            }
            false
        }

    }
}