package at.htl.acornapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.htl.acornapp.Screens.Screen
import at.htl.acornapp.Navigation.MainViewModel
import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetViewModel
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Type
import java.util.*

@Composable
fun CreationAdditionalScreen(
    song: Song?,
    artist: String,
    songName: String,
    type: String,
    mainViewModel: MainViewModel,
    sheetViewModel: SheetViewModel,
    navController: NavController
){


    var songText by remember { mutableStateOf("") }
    var songDuration by remember { mutableStateOf("") }

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .height((screenHeight / 8).dp)
        ) {
            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .width(
                        (((screenWidth / 2) - 36).dp)
                    )
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "Artist:",
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )
                    Text(
                        text = artist,
                        maxLines = 1,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .width(
                        (((screenWidth / 2) - 20).dp)
                    )
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(
                        text = "Song:",
                        fontSize = MaterialTheme.typography.h6.fontSize
                    )

                    if (song != null) {
                        Text(
                            text = song.name,
                            maxLines = 1,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = songName,
                            maxLines = 1,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        OutlinedTextField(
            value = songText,
            onValueChange = {
                    newText -> songText = newText
            },
            label = { Text(text = "Song Text")},
            modifier = Modifier
                .fillMaxWidth()
                .height(((screenHeight / 8) * 5).dp),
            placeholder = {
                if(type.equals("Chords")){
                    Text(text ="(Make sure to mark chord lines with a '%')")
                } else {
                    Text(text ="(Make sure to mark tabs with a '|')")
                }
            },
            textStyle = TextStyle.Default.copy(fontFamily = FontFamily.Monospace)
        )

        Row(modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)) {
            Button(onClick = {
                if (song != null) {
                    if (song.rawLyrics != "") {
                        songText = song.rawLyrics
                    } else {
                        songText = "No Lyrics available..."
                    }
                } else {
                    songText = "No Lyrics available..."
                }
            }


            ) {
                Text(text = "Load raw text")
            }
            OutlinedTextField(
                value = songDuration,
                onValueChange = {newText -> songDuration = newText},
                singleLine = true,
                maxLines = 1,
                label = { Text(text = "Duration in sec")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(30.dp, 0.dp, 0.dp, 0.dp),
                )
        }
        Button(onClick = {
            if(songDuration.isNotEmpty() &&
                songText.isNotEmpty()
            ) {
                val sheet = Sheet(
                    id = 0,
                    song = Song(
                        id = 0,
                        name = songName,
                        rawLyrics = "",
                        avgDuration = songDuration.toInt(),
                        artists = Artist(
                            id = 0,
                            name = artist
                        )
                    ),
                    sheet = songText,
                    chords = "",
                    type = Type.valueOf(type.uppercase(Locale.ROOT))
                )
                mainViewModel.postSheet(
                    sheetViewModel = sheetViewModel,
                    sheet
                )
                mainViewModel.loadSheets(sheetViewModel)
                mainViewModel.getCounts()

                navController.navigate(Screen.Home.route)
            }
        }

        ) {
            Text(text="Save")
        }
    }
}