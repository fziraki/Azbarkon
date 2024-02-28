package abkabk.azbarkon.features.entertainment

import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EntertainmentViewModel @Inject constructor(
): BaseViewModel<EntertainmentViewModel.Events>(){

    init {

    }


    sealed interface Events : Event {

    }



}
