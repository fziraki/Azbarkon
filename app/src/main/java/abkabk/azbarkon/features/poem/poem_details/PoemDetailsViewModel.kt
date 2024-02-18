package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.common.domain.use_case.GetLikedPoemListUseCase
import abkabk.azbarkon.common.domain.use_case.LikePoemUseCase
import abkabk.azbarkon.common.domain.use_case.UnLikePoemUseCase
import abkabk.azbarkon.features.poem.domain.use_case.GetPoemDetailsUseCase
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import abkabk.azbarkon.utils.Constants.POEM
import abkabk.azbarkon.utils.Constants.POEM_ID
import abkabk.azbarkon.utils.Constants.POEM_TITLE
import abkabk.azbarkon.utils.Resource
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
    private val getLikedPoemListUseCase: GetLikedPoemListUseCase,
    private val likePoemUseCase: LikePoemUseCase,
    private val unLikePoemUseCase: UnLikePoemUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(PoemDetailsState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _likedPoemsState = MutableStateFlow<List<PoemDetailsUi>>(emptyList())

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object ToggleBookmark : UiEvent()
    }

    fun onUiEvent(event: UiEvent){
        when(event){
            is UiEvent.ShowSnackbar -> {}
            UiEvent.ToggleBookmark -> {
                toggleLike(
                    state.value.isLiked,
                    state.value.poemDetails!!
                )
            }
        }
    }

    private fun toggleLike(isLiked: Boolean, poem: PoemDetailsUi) {
        if (isLiked){
            unLikePoem(poem.id!!)
        }else{
            likePoem(poem)
        }
    }

    private fun likePoem(poemUi: PoemDetailsUi) {
        viewModelScope.launch {
            likePoemUseCase(poemUi.toPoemDetails()).collect { result ->
                when(result){
                    is Resource.Success -> {
                        getLikedPoems()
                    }
                    is Resource.Error -> {
                        result.message?.let { _uiEvent.emit(UiEvent.ShowSnackbar(it)) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun unLikePoem(poemId: Int) {
        viewModelScope.launch {
            unLikePoemUseCase(poemId).collect { result ->
                when(result){
                    is Resource.Success -> {
                        getLikedPoems()
                    }
                    is Resource.Error -> {
                        result.message?.let { _uiEvent.emit(UiEvent.ShowSnackbar(it)) }
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        savedStateHandle.get<PoemDetailsUi>(POEM)?.let {
            getLikedPoems()
            viewModelScope.launch {
                _likedPoemsState.collect { likedPoems ->
                    val isLiked = likedPoems.any { poem -> poem.id == it.id }
                    _state.value = state.value.copy(
                        isLoading = false,
                        poemDetails = it,
                        poemTitle = it.shortTitle,
                        isLiked = isLiked
                    )
                }
            }
        }?: run {
            savedStateHandle.get<String>(POEM_TITLE)?.let {
                _state.value = state.value.copy(poemTitle = it)
            }
            getLikedPoems()
            savedStateHandle.get<String>(POEM_ID)?.let { poemId ->
                viewModelScope.launch {
                    _likedPoemsState.collect { likedPoems ->
                        val isLiked = likedPoems.any { it.id == poemId.toInt() }
                        _state.value = state.value.copy(isLiked = isLiked)
                    }
                }
                getPoemDetails(poemId.toInt())
            }
        }
    }

    private fun getLikedPoems() {
        viewModelScope.launch {
            getLikedPoemListUseCase().onEach { result ->
                when(result){
                    is Resource.Success -> {
                        _likedPoemsState.value = result.data?.map { it.toPoemDetailsUi() }?: emptyList()
                    }
                    is Resource.Error -> {
                        result.message?.let { _uiEvent.emit(UiEvent.ShowSnackbar(it)) }
                    }
                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun getPoemDetails(poemId: Int) {
        viewModelScope.launch {
            getPoemDetailsUseCase(poemId).onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        val poemDetails = result.data?.apply {
                            this.shortTitle = state.value.poemTitle
                        }
                        _state.value = state.value.copy(isLoading = false,
                            poemDetails = poemDetails?.toPoemDetailsUi()
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