package abkabk.azbarkon.navigation

sealed class Destinations(val route: String) {
    data object PoetListScreen : Destinations("poet_list_screen")

}