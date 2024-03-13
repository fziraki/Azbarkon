package abkabk.azbarkon.features.search.presentation

import abkabk.azbarkon.common.base.BaseViewModel
import abkabk.azbarkon.common.base.Event
import abkabk.azbarkon.features.search.data.repository.SearchPoemByQuerySource
import abkabk.azbarkon.features.search.domain.PoemDetails
import abkabk.azbarkon.features.search.domain.use_case.SearchPoemUseCase
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchPoemUseCase: SearchPoemUseCase
): BaseViewModel<SearchViewModel.Events>(){

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    data class SearchState(
        var isInitialized: Boolean = false
    )

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private lateinit var pagingSource: PagingSource<Int, PoemDetails>

    fun invalidateSource() {
        pagingSource.invalidate()
    }

    init {
        viewModelScope.launch {
            searchText
                .debounce(300)
                .filterNot { it.isEmpty() }
                .distinctUntilChanged()
                .collectLatest {
                    searchPoem()
                }
        }
    }

    lateinit var poemsByFilterPagingFlow: Flow<PagingData<PoemDetails>>

    private fun searchPoem() {

        if (this::pagingSource.isInitialized){
            invalidateSource()
        }

        poemsByFilterPagingFlow = Pager(
            config = PagingConfig(
                pageSize = SearchPoemByQuerySource.PAGE_SIZE,
                prefetchDistance = SearchPoemByQuerySource.PREFETCH_DISTANCE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {

                pagingSource = searchPoemUseCase(
                    term = _searchText.value,
                    poetId = null,
                    catId = null
                )
                pagingSource
            }
        ).flow
            .cachedIn(viewModelScope)

        _state.value = state.value.copy(isInitialized = true)

    }




    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    sealed interface Events : Event {
    }



}
