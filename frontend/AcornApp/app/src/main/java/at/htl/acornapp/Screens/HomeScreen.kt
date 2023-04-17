package at.htl.acornapp.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import at.htl.acornapp.R
import at.htl.acornapp.Screens.Screen
import at.htl.acornapp.Navigation.MainViewModel
import at.htl.acornapp.SheetList
import at.htl.acornapp.TopSheetList
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetViewModel
import at.htl.acornapp.data.Song.SongViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController,
    songViewModel: SongViewModel,
    sheetViewModel: SheetViewModel
) {
    viewModel.loadSongs(songViewModel)
    viewModel.loadSheets(sheetViewModel)

    viewModel.getCounts()

    var learChordsImg = 0;

    if(isSystemInDarkTheme()){
        learChordsImg = R.drawable.learn_chords_dark
    } else {
        learChordsImg = R.drawable.learn_chords
    }

    Scaffold(
    ) { padding ->
        BoxWithConstraints(
            Modifier.padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(Modifier.padding(horizontal = 4.dp)) {
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(8.dp, 4.dp)
                                .width(115.dp)
                                .height(172.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp,
                            border = BorderStroke(3.dp, Color(R.color.light_blue_gray))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Artists",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                                Text(
                                    text = viewModel.artistCount.value.toString(),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_people_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(8.dp, 4.dp)
                                .width(115.dp)
                                .height(172.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp,
                            border = BorderStroke(3.dp, Color(R.color.light_blue_gray))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Songs",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                                Text(
                                    text = viewModel.songCount.value.toString(),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_music_note_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                    Column {
                        Card(
                            modifier = Modifier
                                .padding(8.dp, 4.dp)
                                .width(115.dp)
                                .height(172.dp),
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp,
                            border = BorderStroke(3.dp, Color(R.color.light_blue_gray)),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Sheets",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                                Text(
                                    text = viewModel.sheetCount.value.toString(),
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_article_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(vertical = 15.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
                Row {
                    Card(
                        modifier = Modifier
                            .padding(8.dp, 4.dp)
                            .fillMaxWidth()
                            .height(110.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        border = BorderStroke(3.dp, Color(R.color.light_blue_gray)),
                        onClick = {
                            navController.navigate(
                                route = Screen.Chords.route
                            )
                        }
                    ) {
                        Surface {
                            Image(
                                painter = painterResource(id = learChordsImg),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Learn",
                                        style = MaterialTheme.typography.caption,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }
                                Row {
                                    Text(
                                        text = "Chords",
                                        style = MaterialTheme.typography.caption,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
                Row (Modifier.fillMaxSize()){
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(0.dp, 0.dp, 0.dp, 56.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        viewModel.getTopSheets()

                        val sheets by viewModel.topSheets.observeAsState(listOf())

                        TopSheetList(
                            sheetList = sheets.reversed(),
                            navController = navController,
                            mainViewModel = viewModel)
                        /*Button(onClick = {viewModel.deleteAll()}, ) {
                            Text(text = "Delete")
                        }*/
                    }
                }
            }
        }
    }
}