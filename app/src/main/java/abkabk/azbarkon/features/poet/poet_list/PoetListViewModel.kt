package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.common.base.BaseState
import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.features.poet.domain.use_case.GetPoetListUseCase
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.utils.ErrorEntity
import abkabk.azbarkon.utils.ErrorHandler
import abkabk.azbarkon.utils.Resource
import abkabk.azbarkon.utils.UiText
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
): BaseViewModel<BaseState>(){

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

    fun onUiEvent(event: BaseState) {
        when(event){

            is BaseState.OnError -> {

                if (errorHandler.getError(event.throwable) == ErrorEntity.NoConnection){
                    sendEventSync(event)
                }else{
                    translateError(event.throwable)
                }
            }
            else -> {
                sendEventSync(event)
            }
        }
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
