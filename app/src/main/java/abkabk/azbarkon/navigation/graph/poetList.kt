package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poet.poet_list.PoetListScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poetList(
    navController: NavController,
) {
    composable(
        route = Destinations.PoetListScreen.route
    ) {
        PoetListScreen()
    }
}