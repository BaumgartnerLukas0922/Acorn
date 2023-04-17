package at.htl.acornapp.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChordScreen(){

    val chords = listOf(
        "A7", "A", "Am7", "Am", "Amaj7",
        "B7", "B", "Bm",
        "C7", "C", "Cmaj7", "C#m", "C#m/G#",
        "D7", "D", "Dm7", "Dmaj7", "Dmaj7sus2", "Dsus2",
        "E6", "E7", "E", "Em",
        "F5", "F", "Fm", "Fmaj7", "F#m",
        "G", "G/D")

    val listItems = arrayOf("All", "A", "B", "C", "D", "E", "F");

    var expanded by remember{ mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(listItems[0]) }

    Scaffold (
    ){
            padding ->
        BoxWithConstraints (
            Modifier.padding(padding),
            contentAlignment = Alignment.TopCenter
        ){
            Row(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Chords",
                    style = MaterialTheme.typography.h1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 80.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ){
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {expanded = !expanded}
                ) {
                    TextField(
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(text="Chords")},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {expanded = false}
                    ) {
                        listItems.forEach { selectedOption ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedItem = selectedOption
                                    expanded = false
                                }) {
                                Text(text = selectedOption)
                            }
                        }
                    }
                }
            }
            Row(Modifier.padding(20.dp, 150.dp, 0.dp, 60.dp)) {
                Column(Modifier.fillMaxSize()) {
                    ChordCol(chordList = if (selectedItem == "All") chords else chords.filter { c -> c.startsWith(selectedItem) })
                }
            }
        }
    }

}