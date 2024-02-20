package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poem.poem_list.PoemListScreen
import abkabk.azbarkon.navigation.Destinations
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.poemList(
    navController: NavController,
) {
    composable(
        route = "${Destinations.PoemListScreen().route}/{catId}",
        arguments = listOf(
            navArgument("catId"){ type = NavType.IntType }
        )
    ) {
        PoemListScreen(
            onBackPressed = {
                navController.navigateUp()
            },
            onNavigateToPoemDetails = {

            }
        )
    }
}