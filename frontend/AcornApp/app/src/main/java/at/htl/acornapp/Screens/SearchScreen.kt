package at.htl.acornapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.htl.acornapp.MainAppBar
import at.htl.acornapp.SongList
import at.htl.acornapp.Navigation.MainViewModel
import at.htl.acornapp.Navigation.SearchWidgetState
import at.htl.acornapp.data.Song.Song

@Composable
fun SearchScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    searchResult: List<Song>
){
    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    var text by remember{ mutableStateOf("") }

    Scaffold (
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChanged = {
                    mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchTextState(newValue = "")
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    text = it
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                },
            )
        },
    ){
            padding ->
        BoxWithConstraints (
            Modifier.padding(padding),
            contentAlignment = Alignment.TopCenter
        ){
            Box(
                content = {
                    Surface(color = MaterialTheme.colors.background) {
                        mainViewModel.findSong(searchTextState)
                        SongList(songList = searchResult, navController = navController)
                    }
                },
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 60.dp)
            )
        }
    }

}