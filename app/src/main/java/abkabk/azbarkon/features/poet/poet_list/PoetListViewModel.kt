package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetListViewModel @Inject constructor(
    private val getPoetListUseCase: GetPoetListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PoetListState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getPoets()
    }

    private fun getPoets() {
        viewModelScope.launch {
            getPoetListUseCase().onEach { result ->
                Log.d("tag","result")
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true,
                            poetList = result.data?: emptyList()
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false,
                            poetList = result.data?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false,
                            poetList = result.data?: emptyList()
                        )
                        result.message?.let { _uiEvent.emit(it) }
                    }
                }
            }.launchIn(this)
        }
    }

}