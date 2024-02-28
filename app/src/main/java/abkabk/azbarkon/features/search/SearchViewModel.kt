package abkabk.azbarkon.features.search

import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
): BaseViewModel<SearchViewModel.Events>(){

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {

    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    sealed interface Events : Event {

    }



}
