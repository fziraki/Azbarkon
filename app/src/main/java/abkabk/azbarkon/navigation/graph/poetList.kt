package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.common.base.BaseState
import abkabk.azbarkon.features.poet.poet_list.PoetListScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poetList(
    navController: NavController,
    title: (String) -> Unit,
    onState: (BaseState) -> Unit
) {
    composable(
        route = Destinations.PoetListScreen.route
    ) {
        PoetListScreen(
            title = title,
            onState = onState
        )
    }
}