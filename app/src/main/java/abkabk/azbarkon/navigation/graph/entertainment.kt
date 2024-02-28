package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.entertainment.EntertainmentScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.entertainment(
    navController: NavController,
) {
    composable(
        route = Destinations.EntertainmentScreen.route
    ) {
        EntertainmentScreen(
            onHomeClick = {
                navController.navigate(Destinations.PoetListScreen.route){
                    popUpTo(navController.graph.id)
                }
            },
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}