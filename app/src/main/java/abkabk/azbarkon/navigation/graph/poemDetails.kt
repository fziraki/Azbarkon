package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poem.poem_details.PoemDetailsScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.poemDetails(
    navController: NavController,
) {
    composable(
        route = Destinations.PoemDetailsScreen().route
    ) {
        PoemDetailsScreen(
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}