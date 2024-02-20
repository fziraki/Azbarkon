package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import abkabk.azbarkon.features.poet.domain.use_case.GetPinnedPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.PinPoetListUseCase
import abkabk.azbarkon.features.poet.domain.use_case.UnPinPoetListUseCase
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.utils.ErrorEntity
import abkabk.azbarkon.utils.ErrorHandler
import abkabk.azbarkon.utils.Resource
import abkabk.azbarkon.utils.UiText
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetListViewModel @Inject constructor(
    private val getPinnedPoetListUseCase: GetPinnedPoetListUseCase,
    private val getPoetListUseCase: GetPoetListUseCase,
    private val pinPoetListUseCase: PinPoetListUseCase,
    private val unPinPoetListUseCase: UnPinPoetListUseCase,
    private val errorHandler: ErrorHandler
): BaseViewModel<PoetListViewModel.Events>(){

    private val _poetsToShow = MutableStateFlow<List<PoetUi>>(emptyList())
    val poetsToShow = _poetsToShow.asStateFlow()

    private val _poets = MutableStateFlow<List<PoetUi>>(emptyList())
    val poets = _poets.asStateFlow()

    private val _pinnedPoets = MutableStateFlow<List<Int>>(emptyList())
    val pinnedPoets = _pinnedPoets.asStateFlow()

    init {
        getPinnedPoets()
        getPoets()
        combinedPinsAndPoets()
    }

    private fun combinedPinsAndPoets() {
        viewModelScope.launch {
            combine(
                _poets,
                _pinnedPoets
            ) { poets, pinnedIds ->
                poets.map { poet ->
                    poet.isPinned = pinnedIds.any { it == poet.id }
                    poet
                }.sortedByDescending { it.isPinned }
            }.collect {
                _poetsToShow.value = it
            }
        }
    }

    private fun getPoets(){
        viewModelScope.launch {
            getPoetListUseCase().collect{ result->
                when(result){
                    is Resource.Error -> {


                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {
                        _poets.value = result.data?.map { it.toPoetUi() }?: emptyList()
                    }
                }
            }
        }
    }

    fun onUiEvent(event: Events) {
        when(event){
            is Events.OnSelected -> {
                _poetsToShow.value = _poetsToShow.value.mapIndexed { index, poetUi ->
                    if (event.index == index){
                        poetUi.copy(isSelected = !poetUi.isSelected)
                    }else{
                        poetUi
                    }
                }
            }
            Events.OnCloseClicked -> {
                _poetsToShow.value = _poetsToShow.value.map { poetUi ->
                    poetUi.copy(isSelected = false)
                }
            }
            is Events.OnTogglePin -> {
                if (event.selectedPoets.any {!it.isPinned}){
                    event.selectedPoets.filter {!it.isPinned}.takeIf { it.isNotEmpty() }?.map { it.id!! }
                        ?.let { pinPoets(it) }
                }else{
                    unPinPoets(event.selectedPoets.map { it.id!! })
                }
            }
        }
    }

    private fun unPinPoets(pinnedPoetsIds: List<Int>) {
        viewModelScope.launch {
            unPinPoetListUseCase(pinnedPoetsIds).collect{ result ->
                when(result){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        getPinnedPoets()
                    }
                }
            }
        }
    }

    private fun pinPoets(poetsIds: List<Int>) {
        viewModelScope.launch {
            pinPoetListUseCase(poetsIds).collect{ result ->
                when(result){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        getPinnedPoets()
                    }
                }
            }
        }
    }

    private fun getPinnedPoets() {
        viewModelScope.launch {
            getPinnedPoetListUseCase().collect{ result->
                when(result){
                    is Resource.Error -> {


                    }
                    is Resource.Loading -> {


                    }
                    is Resource.Success -> {
                        _pinnedPoets.value = result.data?: emptyList()
                    }
                }
            }
        }
    }


    sealed interface Events : Event {
        data class OnSelected(var index: Int) : Events
        data object OnCloseClicked : Events

        data class OnTogglePin(val selectedPoets: List<PoetUi>) : Events


    }



    private fun translateError(it: Throwable) {

        val message = when(errorHandler.getError(it)){
            ErrorEntity.BadRequest -> UiText.StringResource(resId = R.string.bad_request)
            ErrorEntity.Forbidden -> UiText.StringResource(resId = R.string.forbidden)
            ErrorEntity.HTTP_401 -> UiText.StringResource(resId = R.string.unauthorized)
            ErrorEntity.NotFound -> UiText.StringResource(resId = R.string.not_found)
            ErrorEntity.ServiceUnavailable -> UiText.StringResource(resId = R.string.service_unavailable)
            ErrorEntity.Unknown -> UiText.StringResource(resId = R.string.unknown)
            else -> {
                UiText.DynamicString("")}
        }

    }



}
