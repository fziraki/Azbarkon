package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.editor.EditorScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.editor(
    navController: NavController,
) {
    composable(
        route = Destinations.EditorScreen.route
    ) {
        EditorScreen(
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