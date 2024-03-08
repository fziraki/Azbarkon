package abkabk.azbarkon.features.entertainment

import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import abkabk.azbarkon.features.entertainment.domain.use_case.GetFaalUseCase
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import abkabk.azbarkon.utils.Resource
import abkabk.azbarkon.utils.UiText
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FaalViewModel @Inject constructor(
    private val getFaalUseCase: GetFaalUseCase,
) : BaseViewModel<FaalViewModel.Events>() {

    private val _state = MutableStateFlow(PoemDetailsState())
    val state = _state.asStateFlow()

    data class PoemDetailsState(
        var isLoading: Boolean = false,
        var poemDetails: PoemDetailsUi? = null,
        var poemTitle: String? = "",
        var isLiked: Boolean = false
    )

    sealed interface Events : Event {
        class Snackbar(var message: UiText) : Events
    }
    fun getFaal() {
        viewModelScope.launch {
            getFaalUseCase().collect { result ->
                when (result) {
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
                    }
                }
            }

        }
    }

}
