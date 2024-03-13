package abkabk.azbarkon.features.search.presentation

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.poet_list.CustomSearchbar
import abkabk.azbarkon.features.search.domain.PoemDetails
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    onNavigateToPoemDetails: (PoemDetails) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsLifecycleAwareState()

    val searchText by viewModel.searchText.collectAsLifecycleAwareState()

    val lazyPagingPoemsByFilter = if (uiState.value.isInitialized){
        viewModel.poemsByFilterPagingFlow.collectAsLazyPagingItems()
    } else {
        null
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.search),
                onHomeClick = onHomeClick,
                onBackPressed = onBackPressed
            )
        },
        containerColor = AzbarkonTheme.colors.background
    ) {



        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()), contentAlignment = Alignment.BottomCenter) {

            if (uiState.value.isInitialized){
                lazyPagingPoemsByFilter?.let {
                    SearchResult(it, onNavigateToPoemDetails = onNavigateToPoemDetails, searchText)
                }
            }

            CustomSearchbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                hint = stringResource(id = R.string.poem_search),
                searchText = searchText,
                onValueChange = viewModel::onSearchTextChange,
                onSearchClick = {
                    keyboardController?.hide()
                    viewModel.invalidateSource()
                },
                onClearClick = {
                    viewModel.onSearchTextChange("")
                }
            )
        }


    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResult(
    lazyPagingPoemsByFilter: LazyPagingItems<PoemDetails>,
    onNavigateToPoemDetails: (PoemDetails) -> Unit,
    searchText: String,
) {



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AzbarkonTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(12.dp),
    ){

            items(
                count = lazyPagingPoemsByFilter.itemCount,
                key = lazyPagingPoemsByFilter.itemKey{ it.id }
            ) { index ->
                val poem = lazyPagingPoemsByFilter[index]
                poem?.let {
                    var thePart = ""
                    val splittedList = poem.plainText?.split("\n")
                    val firstIndex = splittedList?.indexOfFirst { it.contains(searchText)}.takeIf { it != -1 }
                    firstIndex?.let {index ->
                        thePart = if (index.rem(2) ==0){
                            splittedList?.get(index).plus("\n").plus(splittedList?.get(index+1))
                        }else{
                            splittedList?.get(index-1).plus("\n").plus(splittedList?.get(index))
                        }
                    }
                    if (thePart.isNotEmpty()){
                        SearchedPoemItem(
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = {
                                        onNavigateToPoemDetails(it)
                                    }
                                )
                            ,
                            poemName = it.fullTitle?:"",
                            searchQuery = searchText,
                            searchedPart = thePart
                        )
                    }
                }
            }

            when (lazyPagingPoemsByFilter.loadState.refresh) { //FIRST LOAD
                is LoadState.Error -> {
//                            onState(BaseState.OnError((lazyPagingPoemsByFilter.loadState.refresh as LoadState.Error).error))
                }
                is LoadState.Loading -> { // Loading UI
//                            onState(BaseState.OnLoading)
                }
                else -> {

//                            onState(BaseState.OnSuccess)
                }
            }

            when (lazyPagingPoemsByFilter.loadState.append) { // Pagination
                is LoadState.Error -> {
//                            onState(BaseState.OnError((lazyPagingPoemsByFilter.loadState.append as LoadState.Error).error))
                }
                is LoadState.Loading -> { // Pagination Loading UI

                }
                else -> {
//                            onState(BaseState.OnSuccess)
                }
            }

        }

}



