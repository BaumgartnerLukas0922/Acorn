package at.htl.acornapp.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources.NotFoundException
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import at.htl.acornapp.R
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Type
import at.htl.acornapp.pickId
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun SongSheetDetailScreen(
    sheet: Sheet,
){
    val scroll = rememberScrollState(0)
    val coroutineScope = rememberCoroutineScope()
    var scrolling: MutableState<Boolean> = mutableStateOf(false);

    var btnTxtColor = if(isSystemInDarkTheme()) Color.Black else Color.White

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(scroll.isScrollInProgress){
                    coroutineScope.launch {
                        scroll.scrollTo(scroll.value)
                    }
                    scrolling.value = false
                } else {
                    coroutineScope.launch {
                        scroll.animateScrollTo(
                            scroll.maxValue,
                            animationSpec = tween(
                                durationMillis = sheet.song.avgDuration * 1000,
                                easing = LinearEasing
                            )
                        )
                    }
                    scrolling.value = true;
                }
            },
                content = {
                if(scroll.isScrollInProgress && scrolling.value){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_pause_24),
                        contentDescription = null,
                        tint = btnTxtColor,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = btnTxtColor,
                    )
                }
            },
                modifier = Modifier.padding(vertical = 60.dp),
                backgroundColor = MaterialTheme.colors.primary
                )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
            padding ->
        BoxWithConstraints (
            Modifier
                .padding(padding)
                .verticalScroll(scroll),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(modifier = Modifier.padding(vertical = 20.dp)) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    Column() {
                        Text(
                            text = sheet.song.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                        Text(
                            text = sheet.song.artists.name,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (sheet.type == Type.CHORDS) {
                    Chord(chordList = sheet.chords.split(','))

                    Row(modifier = Modifier.padding(vertical = 20.dp, horizontal = 3.dp)) {
                        FormattedSheetChords(sheet = sheet.sheet)
                    }
                } else {
                    Row(modifier = Modifier.padding(vertical = 20.dp, horizontal = 3.dp)) {
                        Text(
                            text = formatTextTabs(sheet.sheet),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp
                        )
                    }
                }
                Row(modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 100.dp)) {

                }
            }
        }
    }
}

@Composable
fun FormattedSheetChords(sheet: String){
    var dark by remember{ mutableStateOf(false) }

    Column{
        sheet.split('[').forEach { item ->
            Text(
                text = formatText("[$item"),
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(
                        if (dark) Color.Transparent else Color.Black.copy(alpha = 0.25f),
                        RectangleShape
                    ).clip(RoundedCornerShape(4.dp))
                    .padding(10.dp)
                    .fillMaxWidth()
            )
            dark = !dark
        }
    }
}

@Composable
fun Chord(chordList: List<String>) {
    LazyRow {
        itemsIndexed(items = chordList) { index, item ->
            ChordItem(item)
        }
    }
}

@Composable
fun ChordCol(chordList: List<String>) {
    LazyColumn {
        itemsIndexed(items = chordList) { index, item ->
            DetailedChordItem(item)
        }
    }
}

@Composable
fun ChordItem(chord: String) {

    val mContext = LocalContext.current

    val colorMatrix = floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )

    Row(modifier = Modifier.fillMaxSize()) {
        if (isSystemInDarkTheme()) {
            Image(
                painter = painterResource(id = getResourceId(mContext, chord, "drawable")),
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
        } else {
            Icon(
                painter = painterResource(id = getResourceId(mContext, chord, "drawable")),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
fun DetailedChordItem(chord: String) {

    val colorMatrix = floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )


    val mContext = LocalContext.current
    val mMediaPlayer = MediaPlayer.create(mContext, getResourceId(mContext, chord, "raw"))

    Row(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxWidth(0.5f)) {
            if (isSystemInDarkTheme()) {
                Image(
                    painter = painterResource(id = getResourceId(mContext, chord, "drawable")),
                    contentDescription = null,
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                )
            } else {
                Icon(
                    painter = painterResource(id = getResourceId(mContext, chord, "drawable")),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
        Column(
            Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)) {
            Button(
                onClick = { mMediaPlayer.start() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.scale(0.5f)) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_baseline_music_note_24
                    ),
                    contentDescription = "",
                    Modifier.size(50.dp),
                    tint = MaterialTheme.colors.background,
                )
            }
        }
    }
}

fun formatText(sheet: String): String{

    val lines = sheet.split('%')
    var formattedLines = ""
    var breakPoint = 0;
    var curLines : List<String>

    if(sheet != "["){
        for ( i in lines.indices){
            formattedLines += if (lines[i].endsWith('\n')) '\n' else ""
            curLines = lines[i].split("\n")
            if(curLines.size>1){
                if(curLines[1].length>40){

                    breakPoint = findLastBreakOption(curLines[1])

                    if(curLines[0].length > breakPoint){
                        formattedLines += curLines[0].substring(0, breakPoint) + '\n'
                        formattedLines += curLines[1].substring(0, breakPoint) + '\n'
                        formattedLines += curLines[0].substring(breakPoint) + '\n'
                        formattedLines += curLines[1].substring(breakPoint) + '\n'
                    } else {
                        formattedLines += curLines[0] + '\n'
                        formattedLines += curLines[1].substring(0, breakPoint) + '\n'
                        formattedLines += '\n' + curLines[1].substring(breakPoint) + '\n'
                    }
                }
                else{
                    formattedLines += curLines[0] + '\n'
                    formattedLines += curLines[1] + '\n'
                }
            } else {
                formattedLines += curLines[0]+'\n'
            }
        }
    }

    return formattedLines
}

fun formatTextTabs(sheet: String): String{

    val curLines = sheet.split('\n')
    var formattedLines = ""
    var i = 0
    var breakPoint = 0

    while (i < curLines.size){

        if(curLines[i].startsWith("|")){
            if(curLines[i].length>45){

                breakPoint = 45

                formattedLines += curLines[i].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i+1].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i+2].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i+3].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i+4].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i+5].substring(0, breakPoint) + "\n\n"
                formattedLines += curLines[i].substring(breakPoint) + '\n'
                formattedLines += curLines[i+1].substring(breakPoint) + '\n'
                formattedLines += curLines[i+2].substring(breakPoint) + '\n'
                formattedLines += curLines[i+3].substring(breakPoint) + '\n'
                formattedLines += curLines[i+4].substring(breakPoint) + '\n'
                formattedLines += curLines[i+5].substring(breakPoint) + '\n'
            }
            else{
                formattedLines += curLines[i] + '\n'
                formattedLines += curLines[i+1] + '\n'
                formattedLines += curLines[i+2] + '\n'
                formattedLines += curLines[i+3] + '\n'
                formattedLines += curLines[i+4] + '\n'
                formattedLines += curLines[i+5] + '\n'
            }
            formattedLines += '\n'
            i+=6;
        } else {
            if(curLines[i].length>45){
                breakPoint = findLastBreakOption(curLines[i])

                formattedLines += curLines[i].substring(0, breakPoint) + '\n'
                formattedLines += curLines[i].substring(breakPoint) + '\n'
            } else {
                formattedLines += curLines[i] + '\n'
            }
            i += 1;
        }
    }

    return formattedLines
}


fun findLastBreakOption(s: String): Int{
    if(s.length-1 > 0) {
        var length = if (s.length <= 45) s.length-1 else 45
        for (i in length downTo 0) {
            if (s[i] == ' ') {
                return i+1
            }
        }
        return 45;
    }
    return s.length
}

@SuppressLint("DiscouragedApi")
fun getResourceId(context: Context, name: String, defType: String): Int {
    if(name == ""){
        return context.resources.getIdentifier("a_icon",defType,context.packageName)
    }

    var convName = name.replace("#", "s")
    convName = convName.replace("/","")

    if(defType == "drawable"){
        convName += "_icon"
    }

    return context.resources.getIdentifier(convName.lowercase(), defType, context.packageName)
}
