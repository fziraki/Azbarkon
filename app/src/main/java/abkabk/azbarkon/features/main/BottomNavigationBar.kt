package abkabk.azbarkon.features.main

import abkabk.azbarkon.navigation.BottomNavItem
import abkabk.azbarkon.ui.theme.AzbarkonTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.PersistentList

@Composable
fun BottomNavigationBar(
    bottomNavColor: Color,
    items: PersistentList<BottomNavItem>,
    currentScreenRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        containerColor = bottomNavColor,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = item.route == (currentScreenRoute?.split("?")?.get(0))
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                            onItemClick(item)
                        },
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                alwaysShowLabel = false,
                selected = isSelected,
                onClick = {},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AzbarkonTheme.colors.selectedNav,
                    selectedTextColor = AzbarkonTheme.colors.selectedNav,
                    unselectedIconColor = AzbarkonTheme.colors.unSelectedNav,
                    unselectedTextColor = AzbarkonTheme.colors.unSelectedNav,
                    indicatorColor = AzbarkonTheme.colors.background
                )
            )
        }
    }
}