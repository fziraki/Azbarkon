package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.common.base.BaseState
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import abkabk.azbarkon.utils.collectAsLifecycleAwareState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PoetListScreen(
    title: (String) -> Unit,
    onState: (BaseState) -> Unit,
    viewModel: PoetListViewModel = hiltViewModel()
){

    val poets = viewModel.poets.collectAsLifecycleAwareState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .background(AzbarkonTheme.colors.background)
            .fillMaxSize(),
        state = lazyStaggeredGridState,
        columns = StaggeredGridCells.Adaptive(92.dp),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalItemSpacing = 8.dp
    ){

        items(
            count = poets.value.size,
            key = { index -> poets.value[index].id?:index }
        ) { index ->
            PoetItem(
                poet = poets.value[index],
                onPoetItemClick = {}
            )
        }

    }



}


