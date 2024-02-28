package abkabk.azbarkon.features.search

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.domain.Poem
import abkabk.azbarkon.features.poet.poet_list.CustomSearchbar
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    onNavigateToPoemDetails: (Poem) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val lazyListState = rememberLazyListState()
    val searchText by viewModel.searchText.collectAsLifecycleAwareState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.search),
                onHomeClick = onHomeClick,
                onBackPressed = onBackPressed
            )
        },
        containerColor = AzbarkonTheme.colors.background
    ) {
        Box(contentAlignment = Alignment.BottomCenter) {
            LazyColumn(
                modifier = Modifier
                    .background(AzbarkonTheme.colors.background)
                    .fillMaxSize(1f)
                    .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
                state = lazyListState,
                contentPadding = PaddingValues(8.dp)
            ){

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
                },
                onClearClick = {
                    viewModel.onSearchTextChange("")
                }
            )
        }


    }


}



