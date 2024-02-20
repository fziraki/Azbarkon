package abkabk.azbarkon.features.poem.poem_list

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.domain.Poem
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PoemListScreen(
    onBackPressed: () -> Unit,
    onNavigateToPoemDetails: (Poem) -> Unit,
    viewModel: PoemListViewModel = hiltViewModel()
) {

    val uiState = viewModel.state.collectAsLifecycleAwareState()

    Scaffold(
        topBar = {
            Toolbar2(title = uiState.value.ancestorName?:"", onBackPressed = onBackPressed)
        },
        containerColor = AzbarkonTheme.colors.background
    ) { paddingValues ->

        val poems = uiState.value.poemList
        poems?.let {
            if (it.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = paddingValues.calculateTopPadding().plus(24.dp)),
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
                            poemName = poems[index].title.plus(" - ").plus(poems[index].excerpt)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Toolbar2(
    title: String,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(AzbarkonTheme.colors.unSelectedNav)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.wrapContentSize(),
            text = title,
            color = AzbarkonTheme.colors.selectedNav,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Icon(
            painterResource(id = R.drawable.ic_back),
            contentDescription = "back",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.clickable { onBackPressed() }
        )


    }
}


