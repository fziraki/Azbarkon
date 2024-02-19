package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.utils.ErrorEntity
import abkabk.azbarkon.utils.ErrorHandler
import abkabk.azbarkon.utils.Resource
import abkabk.azbarkon.utils.UiText
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoetListViewModel @Inject constructor(
    private val getPoetListUseCase: GetPoetListUseCase,
    private val errorHandler: ErrorHandler
): BaseViewModel<PoetListViewModel.Events>(){

    private val _poets = MutableStateFlow<List<PoetUi>>(emptyList())
    val poets = _poets.asStateFlow()

    init {
        getPoets()
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
                _poets.value = _poets.value.mapIndexed { index, poetUi ->
                    if (event.index == index){

                        Log.d("tagg","isSelected ${poetUi.isSelected}")

                        poetUi.copy(isSelected = !poetUi.isSelected)


                    }else{
                        poetUi
                    }
                }


            }

            Events.OnCloseClicked -> {
                _poets.value = _poets.value.map { poetUi ->
                    poetUi.copy(isSelected = false)
                }

            }
            is Events.OnTogglePin -> {
                if (event.selectedPoets.any {!it.isPinned}){
                    pinPoets(event.selectedPoets.filter {!it.isPinned})
                }else{
                    unPinPoets(event.selectedPoets)
                }
            }
        }
    }

    private fun unPinPoets(pinnedPoets: List<PoetUi>) {

    }

    private fun pinPoets(poets: List<PoetUi>) {

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
