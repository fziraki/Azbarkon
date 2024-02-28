package abkabk.azbarkon.features.main

import abkabk.azbarkon.R
import abkabk.azbarkon.library.designsystem.base.BaseRoute
import abkabk.azbarkon.navigation.AzbarkonNavHost
import abkabk.azbarkon.navigation.BottomNavItem
import abkabk.azbarkon.navigation.Destinations
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainScreen() {

    val items = persistentListOf(
        BottomNavItem(
            title = "likes",
            route = Destinations.LikedPoemsScreen.route,
            icon = R.drawable.ic_like_filled,
            badgeCount = 0
        ),
        BottomNavItem(
            title = "search",
            route = Destinations.SearchScreen.route,
            icon = R.drawable.ic_search,
            badgeCount = 0
        ),
        BottomNavItem(
            title = "poets",
            route = Destinations.PoetListScreen.route,
            icon = R.drawable.ic_home,
            badgeCount = 0
        ),
        BottomNavItem(
            title = "entertainment",
            route = Destinations.EntertainmentScreen.route,
            icon = R.drawable.ic_games,
            badgeCount = 0
        ),
        BottomNavItem(
            title = "editor",
            route = Destinations.EditorScreen.route,
            icon = R.drawable.ic_photo,
            badgeCount = 0
        ),

    )

    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        Destinations.LikedPoemsScreen.route -> true
        Destinations.SearchScreen.route -> true
        Destinations.PoetListScreen.route -> true
        Destinations.EntertainmentScreen.route -> true
        Destinations.EditorScreen.route -> true
        else -> false
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                BottomNavigationBar(
                    bottomNavColor = AzbarkonTheme.colors.background,
                    items = items,
                    currentScreenRoute = navBackStackEntry?.destination?.route
                ){
                    navController.navigate(it.route)
                }
            }

        },
        containerColor = AzbarkonTheme.colors.background
    ) { paddingValues ->

        BaseRoute {
            AzbarkonNavHost(
                navController = navController,
                startDestination = Destinations.PoetListScreen.route,
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
    }


}
