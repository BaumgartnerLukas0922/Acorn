package at.htl.acornapp.data

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import at.htl.acornapp.Screens.Screen


@Composable
fun CreationScreen(
    navController: NavController
){
    var artistName by remember { mutableStateOf("") }
    var songName by remember { mutableStateOf("") }

    val radioOptions = listOf("Chords","Tabs")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0] ) }
    var msg by remember {mutableStateOf("")}

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .padding(vertical = 18.dp)
            .fillMaxSize(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .border(
                    5.dp,
                    MaterialTheme.colors.primary
                )
                .padding(horizontal = 18.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add Sheet",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = artistName,
                onValueChange = {
                    newText -> artistName = newText
                },
                label = { Text(text = "Artist")},
                maxLines = 1,
                placeholder = { Text(text = "e.g. Adele")
                },
                keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = songName,
                onValueChange = {
                    newText -> songName = newText
                },
                maxLines = 1,
                label = { Text(text = "Song Title")},
                placeholder = { Text(text = "e.g. Rolling in the Deep")
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if(ValidateEntries(artistName, songName)) {
                            navController.navigate(
                                route = Screen.CreateAddit.passArtistAndSongName(
                                    artistName,
                                    songName,
                                    selectedOption
                                )
                            )
                        }
                    }
                )
            )
            Row {
                radioOptions.forEach { text ->
                    Column() {
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                    }
                                )
                                .padding(horizontal = 2.dp)
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) }
                            )
                            Text(
                                text = text,
                                modifier = Modifier.padding(start = 2.dp, top = 12.dp)
                            )
                        }
                    }
                }
            }
            Button(onClick = {
                if(ValidateEntries(artistName, songName)){
                    navController.navigate(
                        route = Screen.CreateAddit.passArtistAndSongName(
                            artistName,
                            songName,
                            selectedOption
                        )
                    )
                } else {
                    msg = if(!(artistName.isNotBlank() && artistName.isNotEmpty()) &&
                        !(songName.isNotBlank() && songName.isNotEmpty())) "You still have to add the artist's name and the song's title"
                    else if(!(artistName.isNotBlank() && artistName.isNotEmpty())) "You still have to add the artist's name"
                    else "You still have to add the song's title"
                }

            }) {
                Text(text = "Add")
            }
            Row() {
                Text(text = msg, color = Color.Red)
            }
        }
    }
}

fun ValidateEntries(artist: String, song: String) : Boolean{
    if((artist.isNotBlank() && artist.isNotEmpty())
        &&(song.isNotBlank() && song.isNotEmpty())){
        return true;
    }
    return false;
}

@Composable
@Preview(showBackground = true)
fun CreationScreenPreview(){
    CreationScreen(
        navController = rememberNavController()
    )
}