package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.core.Constants.POEM_ID
import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poem.domain.use_case.GetPoemDetailsUseCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoemDetailsViewModel @Inject constructor(
    private val getPoemDetailsUseCase: GetPoemDetailsUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PoemDetailsState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>(POEM_ID)?.let { getPoemDetails(it) }
    }

    private fun getPoemDetails(poemId: Int) {
        viewModelScope.launch {
            getPoemDetailsUseCase(poemId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {

                        _state.value = state.value.copy(isLoading = false,
                            poemDetails = result.data
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