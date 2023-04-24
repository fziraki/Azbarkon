package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.common.Resource
import abkabk.azbarkon.features.poet.domain.use_case.GetPinnedPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.PinPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.UnPinPoetListUseCase
import abkabk.azbarkon.features.poet.model.PoetUi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.Selection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetListViewModel @Inject constructor(
    private val getPoetListUseCase: GetPoetListUseCase,
    private val getPinnedPoetListUseCase: GetPinnedPoetListUseCase,
    private val pinPoetListUseCase: PinPoetListUseCase,
    private val unPinPoetListUseCase: UnPinPoetListUseCase
): ViewModel() {

    private val _state = MutableStateFlow(PoetListState())
    val state = _state.asStateFlow()

    private val _selectedPoetsIds = MutableStateFlow<List<Int>>(emptyList())

    private val _poetsState = MutableStateFlow<List<PoetUi>>(emptyList())
    val poetsState = _poetsState.asStateFlow()

    private val _pinnedPoetsState = MutableStateFlow<List<Int>>(emptyList())
    val pinnedPoetsState = _pinnedPoetsState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getPoets()
        getPinnedPoets()
        combinePoetsToPinnedToGetPoets()
    }

    private fun combinePoetsToPinnedToGetPoets(){
        viewModelScope.launch {
            combine(
                _poetsState,
                _pinnedPoetsState
            ){ poets, pins ->
                poets.map { poet ->
                    poet.isPinned = pins.any { it == poet.id }
                    poet
                }.sortedByDescending { it.isPinned }
            }.collect { poets ->
                _state.value = state.value.copy(poetList = poets)
            }
        }
    }

    private fun getPoets() {
        viewModelScope.launch {
            getPoetListUseCase().onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                        _poetsState.value = result.data?.map { it.toPoetUi() }?: emptyList()
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        _poetsState.value = result.data?.map { it.toPoetUi() }?: emptyList()
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        _poetsState.value = result.data?.map { it.toPoetUi() }?: emptyList()
                        result.message?.let { _uiEvent.emit(it) }
                    }
                }
            }.launchIn(this)
        }
    }
    private fun getPinnedPoets() {
        viewModelScope.launch {
            getPinnedPoetListUseCase().collect { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                        _pinnedPoetsState.value = result.data?: emptyList()
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(isLoading = false)
                        _pinnedPoetsState.value = result.data?: emptyList()
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        _pinnedPoetsState.value = result.data?: emptyList()
                        result.message?.let { _uiEvent.emit(it) }
                    }
                }
            }
        }
    }

    fun saveSelectedItemsIds(selection: Selection<Long>) {
        _selectedPoetsIds.value = selection.map { it.toInt() }
    }

    fun pinUnpin() {
        _state.value.poetList.filter { poet ->
            _selectedPoetsIds.value.any { it == poet.id }
        }.find { !it.isPinned }?.let {
            pinPoets(_selectedPoetsIds.value)
        }?: run {
            unPinPoets(_selectedPoetsIds.value)
        }
    }

    private fun pinPoets(poetIds: List<Int>) {
        viewModelScope.launch {
            pinPoetListUseCase(poetIds).collect { result ->
                when(result){
                    is Resource.Success -> {
                        getPinnedPoets()
                    }
                    is Resource.Error -> {
                        result.message?.let { _uiEvent.emit(it) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun unPinPoets(poetIds: List<Int>) {
        viewModelScope.launch {
            unPinPoetListUseCase(poetIds).collect { result ->
                when(result){
                    is Resource.Success -> {
                        getPinnedPoets()
                    }
                    is Resource.Error -> {
                        result.message?.let { _uiEvent.emit(it) }
                    }
                    else -> {}
                }
            }
        }
    }

}