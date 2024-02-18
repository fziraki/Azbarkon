package abkabk.azbarkon.navigation

import androidx.compose.runtime.Immutable

@Immutable
data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: Int,
    val badgeCount: Int = 0,
)