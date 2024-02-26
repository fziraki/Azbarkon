package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.R
import abkabk.azbarkon.features.poet.model.PoetUi
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun PoetListScreen(
    onBackPressed: () -> Unit,
    onNavigateToPoetDetails: (PoetUi) -> Unit,
    viewModel: PoetListViewModel = hiltViewModel()
) {

    val poets = viewModel.poetsToShow.collectAsLifecycleAwareState()
    val selectedPoets = poets.value.filter { it.isSelected }
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    var isSelectMode by remember { mutableStateOf(false) }
    val searchText by viewModel.searchText.collectAsLifecycleAwareState()
    val keyboardController = LocalSoftwareKeyboardController.current



    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = selectedPoets.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                PinHeader(
                    selectedPoets,
                    onCloseClicked = {
                        viewModel.onUiEvent(PoetListViewModel.Events.OnCloseClicked)
                    },
                    onTogglePin = {
                        viewModel.onUiEvent(PoetListViewModel.Events.OnTogglePin(selectedPoets))
                    }
                )

            }
        }

    ) {

        Box(contentAlignment = Alignment.BottomCenter) {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .background(AzbarkonTheme.colors.background)
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding()),
                state = lazyStaggeredGridState,
                columns = StaggeredGridCells.Adaptive(92.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalItemSpacing = 8.dp
            ) {
                items(
                    count = poets.value.size,
                    key = { index -> poets.value[index].id ?: index }
                ) { index ->
                    PoetItem(
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    isSelectMode = true
                                    viewModel.onUiEvent(PoetListViewModel.Events.OnSelected(index))
                                },
                                onClick = {
                                    if (isSelectMode) {
                                        viewModel.onUiEvent(PoetListViewModel.Events.OnSelected(index))
                                    }else{
                                        onNavigateToPoetDetails(poets.value[index])
                                    }
                                }
                            ),
                        poet = poets.value[index]
                    )
                }

            }

            CustomSearchbar(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                hint = stringResource(id = R.string.poet_name),
                searchText = searchText,
                onValueChange = viewModel::onSearchTextChange,
                onSearchClick = {
                    keyboardController?.hide()
                }
            )
        }



    }


}

@Composable
fun PinHeader(
    selectedPoets: List<PoetUi>,
    onCloseClicked: () -> Unit,
    onTogglePin: () -> Unit
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
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onTogglePin()
                }
                .wrapContentSize(),
            text = if (selectedPoets.any { !it.isPinned }) {
                stringResource(R.string.pin)
            } else {
                stringResource(R.string.unpin)

            },
            style = AzbarkonTheme.typography.title1,
            color = AzbarkonTheme.colors.selectedNav,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )

        Row(
            modifier = Modifier
                .clickable { onCloseClicked() }
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            Text(
                modifier = Modifier.wrapContentSize(),
                text = selectedPoets.size.toString(),
                color = AzbarkonTheme.colors.selectedNav,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                painterResource(id = R.drawable.ic_close),
                contentDescription = "close icon",
                tint = AzbarkonTheme.colors.selectedNav
            )
        }
    }
}


