package abkabk.azbarkon.navigation

sealed class Destinations(val route: String) {
    data object PoetListScreen : Destinations("poet_list_screen")
    data class PoetDetailsScreen(val poet: String = "poet") : Destinations("poet_details_screen")
    data class PoemListScreen(val catId: Int? = null) : Destinations("poem_list_screen")
    data class PoemDetailsScreen(
        val poem: String = "poem",
//        val poemId: String? = "poemId",
//        val poemTitle: String? = "poemTitle"
    ) : Destinations("poem_details_screen")

    data object LikedPoemsScreen : Destinations("liked_poems_screen")

    data object EditorScreen : Destinations("editor_screen")


}