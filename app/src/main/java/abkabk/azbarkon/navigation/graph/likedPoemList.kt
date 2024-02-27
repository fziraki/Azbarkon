package abkabk.azbarkon.navigation.graph

import abkabk.azbarkon.features.likes.LikedPoemsScreen
import abkabk.azbarkon.navigation.Destinations
import abkabk.azbarkon.navigation.navigate
import abkabk.azbarkon.utils.navToJson
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.likedPoemList(
    navController: NavController,
) {
    composable(
        route = Destinations.LikedPoemsScreen.route
    ) {
        LikedPoemsScreen(
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