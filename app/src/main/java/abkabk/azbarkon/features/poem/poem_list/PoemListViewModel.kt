package abkabk.azbarkon.features.poem.poem_list

import abkabk.azbarkon.features.poem.domain.use_case.GetPoemsUseCase
import abkabk.azbarkon.utils.Constants
import abkabk.azbarkon.utils.Resource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoemListViewModel @Inject constructor(
    private val getPoemsUseCase: GetPoemsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PoemListState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>(Constants.CAT_ID)?.let { getPoems(it) }
    }

    private fun getPoems(catId: Int) {
        viewModelScope.launch {
            getPoemsUseCase(catId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val ancestors = result.data?.cat?.ancestors
                        val builder = StringBuilder()
                        ancestors?.forEach {
                            builder.append(String.format("%s » ", it.title))
                        }
                        _state.value = state.value.copy(
                            isLoading = false,
                            ancestorName = builder.toString().plus(result.data?.cat?.title),
                            poemList = result.data?.cat?.poems
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        result.message?.let { _uiEvent.emit(it) }
                    }
                }
            }.launchIn(this)
        }
    }



}