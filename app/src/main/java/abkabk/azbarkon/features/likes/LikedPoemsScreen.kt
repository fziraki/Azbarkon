package abkabk.azbarkon.features.likes

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import abkabk.azbarkon.features.poem.poem_list.PoemItem
import abkabk.azbarkon.library.designsystem.base.Toolbar
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LikedPoemsScreen(
    onHomeClick: () -> Unit,
    onBackPressed: () -> Unit,
    onNavigateToPoemDetails: (PoemDetailsUi) -> Unit,
    viewModel: LikesViewModel = hiltViewModel()
){

    LaunchedEffect(Unit){
        viewModel.getLikedPoems()
    }

    val uiState = viewModel.state.collectAsLifecycleAwareState()

    Scaffold(
        topBar = {
            Toolbar(
                title = stringResource(id = R.string.likes),
                onHomeClick = onHomeClick,
                onBackPressed = onBackPressed
            )
        },
        containerColor = AzbarkonTheme.colors.background
    ) { paddingValues ->

        val poems = uiState.value.poemList
        poems?.let {
            if (it.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues
                                .calculateTopPadding()
                                .plus(24.dp)
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    items(
                        count = it.size,
                        key = { index -> it[index].id }
                    ){index ->
                        PoemItem(
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = {
                                        onNavigateToPoemDetails(it[index])
                                    }
                                )
                            ,
                            poemName = poems[index].fullTitle?:""
                        )
                    }
                }
            }

        }

    }
}