package at.htl.acornapp.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Search: BottomBarScreen(
        route="search_screen",
        title = "Search",
        icon = Icons.Default.Search
    )object Home: BottomBarScreen(
        route="home_screen",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Create: BottomBarScreen(
        route="create_screen",
        title = "Add New",
        icon = Icons.Default.Create
    )
}