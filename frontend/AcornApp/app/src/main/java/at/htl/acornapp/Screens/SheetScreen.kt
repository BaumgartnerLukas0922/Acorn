package at.htl.acornapp.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.htl.acornapp.SheetList
import at.htl.acornapp.Navigation.MainViewModel
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Song.Song

@Composable
fun SheetScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    song: Song,
    sheets: List<Sheet>
) {
    mainViewModel.findSheetsForSong(song.id)

    Scaffold{
            padding ->
        BoxWithConstraints (
            Modifier.padding(padding),
            contentAlignment = Alignment.TopCenter
        ){
            Box(
                content = {
                    Surface(color = MaterialTheme.colors.background) {
                        SheetList(
                            sheetList = sheets.filter
                            {
                                    s -> s.song.id == song.id
                            },
                            navController = navController,
                            mainViewModel = mainViewModel
                        )
                    }
                },
                contentAlignment = Alignment.TopCenter
            )
        }
    }
}