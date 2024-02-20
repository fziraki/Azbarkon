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
import androidx.compose.runtime.remember
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
            title = "poets",
            route = Destinations.PoetListScreen.route,
            icon = R.drawable.ic_home,
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
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreenRoute = backStackEntry.value?.destination?.route

    val bottomBarVisibility by remember { mutableStateOf(true)}


    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                BottomNavigationBar(
                    bottomNavColor = AzbarkonTheme.colors.background,
                    items = items,
                    currentScreenRoute = currentScreenRoute
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
