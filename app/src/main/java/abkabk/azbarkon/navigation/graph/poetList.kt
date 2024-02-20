package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poet.poet_list.PoetListScreen
import abkabk.azbarkon.navigation.Destinations
import abkabk.azbarkon.navigation.navigate
import abkabk.azbarkon.utils.navToJson
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poetList(
    navController: NavController,
) {
    composable(
        route = Destinations.PoetListScreen.route
    ) {
        PoetListScreen(
            onNavigateToPoetDetails = { poet ->
                navController.navigate(
                    route = "${Destinations.PoetDetailsScreen().route}?poet={poet}",
                    args = bundleOf(Destinations.PoetDetailsScreen().poet to poet.navToJson())
                )
            }
        )
    }
}