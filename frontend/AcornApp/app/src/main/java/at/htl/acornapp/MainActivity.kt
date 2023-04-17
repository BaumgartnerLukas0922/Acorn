package at.htl.acornapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import at.htl.acornapp.Screens.Screen
import at.htl.acornapp.Navigation.MainViewModel
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetViewModel
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Song.SongViewModel
import at.htl.acornapp.data.TopSheet.TopSheet
import at.htl.acornapp.ui.theme.AcornTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        actionBar?.hide()

        super.onCreate(savedInstanceState)
        setContent {
            AcornTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SheetItem(
    sheet: Sheet,
    song: Song,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        onClick = {
            mainViewModel.addToTopSheets(sheet)
            navController.navigate(
                route = Screen.SheetDetail.passSheetId(
                    sheet.id
                )
            )
        }
    ) {
        Surface() {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = song.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = sheet.chords,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = sheet.type.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TopSheetItem(
    sheet: Sheet,
    song: Song,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        border = BorderStroke(2.dp, Color(R.color.light_blue_gray)),
        onClick = {
            mainViewModel.addToTopSheets(sheet)
            navController.navigate(
                route = Screen.SheetDetail.passSheetId(
                    sheet.id
                )
            )
        }
    ) {
        Surface() {
            Canvas(Modifier.fillMaxSize()){
                val canvasWidth = size.width
                val canvasHeight = size.height

                val trianglePath = Path().apply {
                    moveTo(canvasWidth, 0f)
                    lineTo(15*canvasWidth/20, 0f)
                    lineTo(16*canvasWidth/20, 3*canvasHeight/20)
                    lineTo(15*canvasWidth/20, 6*canvasHeight/20)
                    lineTo(canvasWidth, 6*canvasHeight/20)
                }

                drawPath(
                    trianglePath,
                    color = Color(0xFFF44336)
                )

            }
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {
                    Text(
                        text = song.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = sheet.chords,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = sheet.type.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SheetList(
    sheetList: List<Sheet>,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    LazyColumn {
        itemsIndexed(items = sheetList) { index, item ->
            SheetItem(sheet = Sheet(
                item.id,
                item.song,
                item.sheet,
                item.chords,
                item.type
            ),
                item.song,
                navController,
                mainViewModel
            )
        }
    }
}

@Composable
fun TopSheetList(
    sheetList: List<TopSheet>,
    navController: NavController,
    mainViewModel: MainViewModel
) {
    LazyColumn {
        itemsIndexed(items = sheetList) { index, item ->
            TopSheetItem(sheet = item.sheet,
                item.sheet.song,
                navController,
                mainViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongItem(song: Song, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .fillMaxWidth()
            .height(110.dp), shape = RoundedCornerShape(8.dp), elevation = 4.dp,
        onClick = {
            navController.navigate(
                route = Screen.Sheet.passSheetId(
                    song.id
                )
            )
        }
    ) {
        Surface() {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxHeight()
                        .weight(0.8f)
                ) {

                    Text(
                        text = song.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = song.artists.name,
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SongList(songList: List<Song>, navController: NavController) {
    LazyColumn {
        itemsIndexed(items = songList) { index, item ->
            SongItem(song = Song(
                item.id,
                item.name,
                item.artists,
                item.rawLyrics,
                item.avgDuration
            ), navController)
        }
    }
}

class MainViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}

class SongViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SongViewModel() as T
    }
}

class SheetViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SheetViewModel() as T
    }
}

@Composable
fun ScreenSetup() {
    MainScreen()
}

fun pickId(chord: String): Int{
    if(chord.equals("A7"))
        return R.drawable.a7_icon
    if(chord.equals("A"))
        return R.drawable.a_icon
    if(chord.equals("Am7"))
        return R.drawable.am7_icon
    if(chord.equals("Am"))
        return R.drawable.am_icon
    if(chord.equals("Amaj7"))
        return R.drawable.amaj7_icon
    if(chord.equals("B7"))
        return R.drawable.b7_icon
    if(chord.equals("B"))
        return R.drawable.b_icon
    if(chord.equals("Bm"))
        return R.drawable.bm_icon
    if(chord.equals("C7"))
        return R.drawable.c7_icon
    if(chord.equals("C"))
        return R.drawable.c_icon
    if(chord.equals("Cmaj7"))
        return R.drawable.cmaj7_icon
    if(chord.equals("C#m"))
        return R.drawable.csm_icon
    if(chord.equals("D7"))
        return R.drawable.d7_icon
    if(chord.equals("D"))
        return R.drawable.d_icon
    if(chord.equals("Dm7"))
        return R.drawable.dm7_icon
    if(chord.equals("E7"))
        return R.drawable.e7_icon
    if(chord.equals("E"))
        return R.drawable.e_icon
    if(chord.equals("Em"))
        return R.drawable.em_icon
    if(chord.equals("F"))
        return R.drawable.f_icon
    if(chord.equals("Fm"))
        return R.drawable.fm_icon
    if(chord.equals("Fmaj7"))
        return R.drawable.fmaj7_icon
    if(chord.equals("F#m"))
        return R.drawable.fsm_icon
    if(chord.equals("G"))
        return R.drawable.g_icon
    if(chord.equals("G/D"))
        return R.drawable.gd_icon
    return R.drawable.ic_add
}