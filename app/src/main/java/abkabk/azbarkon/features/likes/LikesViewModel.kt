package abkabk.azbarkon.features.likes

import abkabk.azbarkon.common.domain.use_case.GetLikedPoemListUseCase
import abkabk.azbarkon.utils.Resource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikesViewModel @Inject constructor(
    private val getLikedPoemListUseCase: GetLikedPoemListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(LikesState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object OnSetupScreen: UiEvent()
//        object ToggleBookmark : UiEvent()
    }

    fun onUiEvent(event: UiEvent){
        when(event){
            is UiEvent.ShowSnackbar -> {}
            UiEvent.OnSetupScreen -> getLikedPoems()
//            UiEvent.ToggleBookmark -> toggleLike(state.value.isLiked, state.value.poemDetails?.id!!)
        }
    }

    private fun getLikedPoems() {
        viewModelScope.launch {
            getLikedPoemListUseCase().onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            poemList = result.data?.map { it.toPoemDetailsUi() }?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        result.message?.let { _uiEvent.emit(UiEvent.ShowSnackbar(it)) }
                    }
                }
            }.launchIn(this)
        }
    }



}