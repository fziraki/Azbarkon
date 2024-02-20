package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.R
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.LoadImage
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
fun PoetDetailsScreen(
    onBackPressed: () -> Unit,
    onNavigateToPoemList: (Int?) -> Unit,
    viewModel: PoetDetailsViewModel = hiltViewModel()
) {

    val uiState = viewModel.state.collectAsLifecycleAwareState()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Toolbar(
                    title = uiState.value.ancestorName ?: "",
                    onBackPressed = {
                        if (uiState.value.poet?.rootCatId == uiState.value.catId) {
                            onBackPressed()
                        } else {
                            viewModel.restore()
                        }
                    }
                )
                LoadImage(
                    modifier = Modifier.size(80.dp),
                    url = uiState.value.poet?.imageUrl ?: "",
                    errorImage = ""
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = uiState.value.poet?.name ?: "",
                    style = AzbarkonTheme.typography.title1,
                    color = AzbarkonTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis
                )
            }

        },
        containerColor = AzbarkonTheme.colors.background
    ) { paddingValues ->

        val children = uiState.value.children
        children?.let {
            if (it.isEmpty()) {
                viewModel.restore()
                onNavigateToPoemList(uiState.value.catId)
            } else {
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
                ) {
                    items(
                        count = it.size,
                        key = { index -> it[index].id }
                    ) { index ->
                        CategoryItem(
                            modifier = Modifier
                                .combinedClickable(
                                    onLongClick = {

                                    },
                                    onClick = {
                                        viewModel.getCategories(it[index].id)
                                    }
                                ),
                            categoryName = children[index].title
                        )
                    }
                }
            }
        }
    }

    BackHandler {
        if (uiState.value.poet?.rootCatId == uiState.value.catId) {
            onBackPressed()
        } else {
            viewModel.restore()
        }
    }
}

@Composable
fun Toolbar(
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

        Row(
            modifier = Modifier
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {

            Icon(
                painterResource(id = R.drawable.ic_return_home),
                contentDescription = "return home",
                tint = AzbarkonTheme.colors.selectedNav
            )
            Text(
                modifier = Modifier.wrapContentSize(),
                text = title,
                color = AzbarkonTheme.colors.selectedNav,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )

        }

        Icon(
            painterResource(id = R.drawable.ic_back),
            contentDescription = "back",
            tint = AzbarkonTheme.colors.selectedNav,
            modifier = Modifier.clickable { onBackPressed() }
        )


    }
}