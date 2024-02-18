package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.features.poet.domain.use_case.GetSubCategoriesUseCase
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.features.poet.poet_details.memento.Editor
import abkabk.azbarkon.features.poet.poet_details.memento.History
import abkabk.azbarkon.utils.Resource
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetDetailsViewModel @Inject constructor(
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
    private val editor: Editor,
    private val history: History,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PoetDetailsState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        data class Navigate(val action: Int, val bundle: Bundle) : UiEvent()
    }
    init {
        savedStateHandle.get<PoetUi>("poet")?.let {
            it.rootCatId?.let { rootCatId ->
                _state.value = state.value.copy(poet = it, catId = rootCatId)
                getCategories(rootCatId)
            }
        }
    }

    fun getCategories(catId: Int) {
        viewModelScope.launch {
            getSubCategoriesUseCase(catId).collect { result ->
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
                        val currentState = state.value.copy(isLoading = false,
                            catId = catId,
                            ancestorName = builder.toString().plus(result.data?.cat?.title),
                            children = result.data?.cat?.children,
                        )
                        //save state before set it because of empty categories things go wrong
                        saveState(currentState)
                        _state.value = currentState
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        result.message?.let { _eventFlow.emit(UiEvent.ShowSnackbar(it)) }
                    }
                }
            }
        }
    }

    fun restore() {
        editor.restoreState(history.pop())
        editor.contenttt.asStateFlow().onEach {
            _state.value = state.value.copy(
                catId = it.catId,
                ancestorName = it.ancestorName,
                children = it.children,
            )
        }.launchIn(viewModelScope)
    }

    fun shouldNavigate(action: Int, bundle: Bundle) {
        viewModelScope.launch {
            restore()
            _eventFlow.emit(UiEvent.Navigate(action, bundle))
        }
    }

    private fun saveState(currentState: PoetDetailsState) {
        editor.content = currentState
        history.push(editor.createState())
    }


}