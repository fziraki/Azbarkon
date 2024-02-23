package abkabk.azbarkon.features.poem.poem_details

import abkabk.azbarkon.common.domain.use_case.GetLikedPoemListUseCase
import abkabk.azbarkon.common.domain.use_case.LikePoemUseCase
import abkabk.azbarkon.common.domain.use_case.UnLikePoemUseCase
import abkabk.azbarkon.features.poem.domain.use_case.GetPoemDetailsUseCase
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import abkabk.azbarkon.utils.Constants.POEM
import abkabk.azbarkon.utils.Resource
import abkabk.azbarkon.utils.navFromJson
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _uiEvent = MutableSharedFlow<Events>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _likedPoemsState = MutableStateFlow<List<PoemDetailsUi>>(emptyList())

    sealed class Events {
        data class ShowSnackbar(val message: String): Events()
        data object ToggleLike : Events()
    }

    fun onUiEvent(event: Events){
        when(event){
            is Events.ShowSnackbar -> {}
            Events.ToggleLike -> {
                state.value.poemDetails?.let {
                    toggleLike(state.value.isLiked, it)
                }
            }
        }
    }

    private fun toggleLike(isLiked: Boolean, poem: PoemDetailsUi) {
        if (isLiked){
            unLikePoem(poem.id)
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
                        result.message?.let { _uiEvent.emit(Events.ShowSnackbar(it)) }
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
                        result.message?.let { _uiEvent.emit(Events.ShowSnackbar(it)) }
                    }
                    else -> {}
                }
            }
        }
    }

    init {
        savedStateHandle.get<String>(POEM)?.let {
            getLikedPoems()
            it.navFromJson(PoemDetailsUi::class.java).let { poem ->
                viewModelScope.launch {
                    _likedPoemsState.collect { likedPoems ->
                        val isLiked = likedPoems.any { it.id == poem.id }
                        _state.value = state.value.copy(isLiked = isLiked)
                    }
                }
                getPoemDetails(poem.id)
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
                        result.message?.let { _uiEvent.emit(Events.ShowSnackbar(it)) }
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
                        _state.value = state.value.copy(
                            isLoading = false,
                            poemDetails = result.data?.toPoemDetailsUi(),
                            poemTitle = result.data?.fullTitle
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(isLoading = false)
                        result.message?.let { _uiEvent.emit(Events.ShowSnackbar(it)) }
                    }
                }
            }.launchIn(this)
        }
    }


}