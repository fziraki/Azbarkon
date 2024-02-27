package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.poem.poem_list.PoemListScreen
import abkabk.azbarkon.navigation.Destinations
import abkabk.azbarkon.navigation.navigate
import abkabk.azbarkon.utils.navToJson
import androidx.core.os.bundleOf
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
            onHomeClick = {
                navController.navigate(Destinations.PoetListScreen.route){
                    popUpTo(navController.graph.id)
                }
            },
            onBackPressed = {
                navController.navigateUp()
            },
            onNavigateToPoemDetails = { poem ->
                navController.navigate(
                    route = "${Destinations.PoemDetailsScreen().route}?poem={poem}",
                    args = bundleOf(Destinations.PoemDetailsScreen().poem to poem.navToJson())
                )
            }
        )
    }
}