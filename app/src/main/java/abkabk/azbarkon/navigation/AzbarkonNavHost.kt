package abkabk.azbarkon.navigation

import abkabk.azbarkon.navigation.graph.likedPoemList
import abkabk.azbarkon.navigation.graph.poemDetails
import abkabk.azbarkon.navigation.graph.poemList
import abkabk.azbarkon.navigation.graph.poetDetails
import abkabk.azbarkon.navigation.graph.poetList
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AzbarkonNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {

        poetList(navController)
        poetDetails(navController)
        poemList(navController)
        poemDetails(navController)
        likedPoemList(navController)
    }
}