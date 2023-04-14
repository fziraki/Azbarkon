package abkabk.azbarkon.features.poem.poem_list

import abkabk.azbarkon.R
import abkabk.azbarkon.core.SpaceItemDecoration
import abkabk.azbarkon.core.base.BaseFragment
import abkabk.azbarkon.core.extension.autoCleared
import abkabk.azbarkon.core.extension.viewBinding
import abkabk.azbarkon.databinding.FragmentPoemListBinding
import abkabk.azbarkon.features.poet.domain.Poem
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
class PoemListFragment: BaseFragment(R.layout.fragment_poem_list) {

    override var bottomNavigationViewVisibility = View.GONE
    private val viewBinding by viewBinding(FragmentPoemListBinding::bind)
    private val viewModel: PoemListViewModel by viewModels()

    private var poemsAdapter by autoCleared<PoemsAdapter>()
    private val poemItemClickListener = { poem: Poem ->
        findNavController()
            .navigate(R.id.action_poemList_to_poemDetailsFragment,
                bundleOf("poemId" to poem.id)
            )
    }

    override fun setupScreen() {
        initRecyclerView()
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

                viewBinding.title.text = it.ancestorName

                it.poemList?.let { poems ->
                    poemsAdapter.setData(poems)
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.uiEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

    }


    private fun initRecyclerView() {
        poemsAdapter = PoemsAdapter(poemItemClickListener)
        with(viewBinding.poemListRv) {
            adapter = poemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutDirection = View.LAYOUT_DIRECTION_RTL
            addItemDecoration(SpaceItemDecoration(10))
        }
    }}