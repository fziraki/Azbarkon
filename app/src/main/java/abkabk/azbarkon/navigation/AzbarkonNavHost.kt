package abkabk.azbarkon.navigation

import abkabk.azbarkon.common.base.BaseState
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
    title: (String) -> Unit,
    onState: (BaseState) -> Unit,
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

        poetList(navController, title, onState)

    }
}