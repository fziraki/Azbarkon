package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.core.Constants.POET_ID
import abkabk.azbarkon.core.Resource
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetDetailsUseCase
import abkabk.azbarkon.features.poet.domain.use_case.GetSubCategoriesUseCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetDetailsViewModel @Inject constructor(
    private val getPoetDetailsUseCase: GetPoetDetailsUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PoetDetailsState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<Int>(POET_ID)?.let { getPoetDetails(it) }
    }

    private fun getPoetDetails(poetId: Int) {
        viewModelScope.launch {
            getPoetDetailsUseCase(poetId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val ancestors = result.data?.cat?.ancestors
                        _state.value = state.value.copy(isLoading = false,
                            poetImage = result.data?.poet?.loadableImageUrl,
                            poetName = result.data?.poet?.name,
                            catId = if (ancestors.isNullOrEmpty()){null}else{ancestors[ancestors.lastIndex].id},
                            children = result.data?.cat?.children,
                            poems = result.data?.cat?.poems
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

    fun getCategories(childId: Int) {
        viewModelScope.launch {
            getSubCategoriesUseCase(childId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val ancestors = result.data?.cat?.ancestors
                        _state.value = state.value.copy(isLoading = false,
                            catId = if (ancestors.isNullOrEmpty()){null}else{ancestors[ancestors.lastIndex].id},
                            childId = childId,
                            children = result.data?.cat?.children,
                            poems = result.data?.cat?.poems
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