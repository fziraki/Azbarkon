//package abkabk.azbarkon.features.poem.poem_details
//
//import abkabk.azbarkon.R
//import abkabk.azbarkon.common.extension.viewBinding
//import abkabk.azbarkon.databinding.FragmentPoemDetailsBinding
//import android.view.View
//import android.widget.Toast
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.flowWithLifecycle
//import androidx.lifecycle.lifecycleScope
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//
//@AndroidEntryPoint
//class PoemDetailsFragment: BaseFragment(R.layout.fragment_poem_details) {
//
//    private val viewBinding by viewBinding(FragmentPoemDetailsBinding::bind)
//    private val viewModel: PoemDetailsViewModel by viewModels()
//
//    override fun setupScreen() {
//
//        viewBinding.toggleBookmark.setOnClickListener {
//            viewModel.onUiEvent(PoemDetailsViewModel.UiEvent.ToggleBookmark)
//        }
//
//    }
//
//    override fun setupObservers() {
//
//        viewModel.state
//            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
//            .onEach { poemDetailsState ->
//
//                if (poemDetailsState.isLoading){
//                    showLoading()
//                }else{
//                    hideLoading()
//                }
//
//                poemDetailsState.poemTitle?.let {
//                    initTitle(it, View.VISIBLE, View.VISIBLE)
//                }
//
//                if (poemDetailsState.isLiked) {
//                    viewBinding.toggleBookmark.setImageResource(R.drawable.ic_like_filled)
//                } else {
//                    viewBinding.toggleBookmark.setImageResource(R.drawable.ic_like_border)
//                }
//
//                viewBinding.poem.apply {
//                    settings.setSupportZoom(true)
//                    setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.color_c7b299))
//                    poemDetailsState.poemDetails?.htmlText?.let {
//                        val html = it
//                            .replace("class=\"m1\"","style=\'text-align: right;\'")
//                            .replace("class=\"m2\"","style=\'text-align: left;\'")
//                            .replace("class=\"b2\"","style=\'text-align: center;\'")
//                        loadData(html, "text/html", "UTF-8")
//                    }
//                }
//
//            }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//        viewModel.uiEvent
//            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
//            .onEach { uiEvent ->
//                when(uiEvent){
//                    is PoemDetailsViewModel.UiEvent.ShowSnackbar -> {
//                        Toast.makeText(requireContext(), uiEvent.message, Toast.LENGTH_SHORT).show()
//                    }
//                    else -> {}
//                }
//            }.launchIn(viewLifecycleOwner.lifecycleScope)
//
//    }
//
//
//}