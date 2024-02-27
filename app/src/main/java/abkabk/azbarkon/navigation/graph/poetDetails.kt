package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poet.poet_details.PoetDetailsScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poetDetails(
    navController: NavController,
) {
    composable(
        route = Destinations.PoetDetailsScreen().route
    ) {
        PoetDetailsScreen(
            onHomeClick = {
                navController.navigate(Destinations.PoetListScreen.route){
                    popUpTo(navController.graph.id)
                }
            },
            onBackPressed = {
                navController.navigateUp()
            },
            onNavigateToPoemList = { catId ->
                navController.navigate(
                    route = "${Destinations.PoemListScreen().route}/${catId}",
                )
            }
        )
    }
}