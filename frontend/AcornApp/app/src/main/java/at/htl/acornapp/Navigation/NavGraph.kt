package at.htl.acornapp.Navigation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.window.SplashScreen
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import at.htl.acornapp.*
import at.htl.acornapp.Screens.*
import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.CreationScreen
import at.htl.acornapp.data.HomeScreen
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetViewModel
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Song.SongViewModel
import at.htl.acornapp.data.Type

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(
            route = Screen.Home.route
        ){
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )
                val songViewModel: SongViewModel = viewModel(
                    it,
                    "SongViewModel",
                    SongViewModelFactory()
                )
                val sheetViewModel: SheetViewModel = viewModel(
                    it,
                    "SheetViewModel",
                    SheetViewModelFactory()
                )

                HomeScreenSetup(viewModel, navController, songViewModel, sheetViewModel)
            }
        }
        composable(
            route = Screen.Create.route
        ){
            CreationScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Search.route
        ){
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

                SearchScreenSetup(viewModel, navController)
            }
        }
        composable(
            route = Screen.CreateAddit.route,
            arguments = listOf(navArgument(ARTIST_NAME_ARGUMENT){
                type = NavType.StringType
            }, navArgument(SONG_NAME_ARGUMENT){
                type = NavType.StringType
            })
        ){
            var artist = it.arguments?.getString(ARTIST_NAME_ARGUMENT).toString()
            var song = it.arguments?.getString(SONG_NAME_ARGUMENT).toString()
            var type = it.arguments?.getString(TYPE_ARGUMENT).toString()

            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )
                val sheetViewModel: SheetViewModel = viewModel(
                    it,
                    "SheetViewModel",
                    SheetViewModelFactory()
                )

                CreationAdditionScreenSetup(viewModel, sheetViewModel, navController, artist, song, type)
            }
        }
        composable(
            route = Screen.Sheet.route,
            arguments = listOf(navArgument(SHEET_ID_ARGUMENT){
                type = NavType.IntType
            })
        ){
            Log.d("Args", it.arguments?.getInt(SHEET_ID_ARGUMENT).toString())
            val argument = it.arguments?.getInt(SHEET_ID_ARGUMENT)!!
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

                SheetScreenSetup(viewModel, navController, argument)
            }
        }
        composable(
            route = Screen.SheetDetail.route,
            arguments = listOf(navArgument(SHEET_ID_ARGUMENT){
                type = NavType.IntType
            })
        ){
            Log.d("Args", it.arguments?.getInt(SHEET_ID_ARGUMENT).toString())
            val argument = it.arguments?.getInt(SHEET_ID_ARGUMENT)!!
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: MainViewModel = viewModel(
                    it,
                    "MainViewModel",
                    MainViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )

                SheetDetailScreenSetup(viewModel, argument)
            }
        }
        composable(
            route = Screen.Chords.route
        )
        {
            ChordScreenSetup()
        }
        composable(
            route = Screen.Splash.route
        ) {
            SplashScreen(navController = navController)
        }
    }
}

@Composable
fun SheetDetailScreenSetup(viewModel: MainViewModel, argument: Int) {
    viewModel.findSheet(argument)
    val sheet by viewModel.sheet.observeAsState(
        Sheet(
            sheet = "",
            song = Song(
                artists = Artist(0,""),
                avgDuration = 0,
                rawLyrics = "",
                name = "",
                id = 0
            ),
            id = 0,
            chords = "",
            type=Type.CHORDS
        )
    )

    SongSheetDetailScreen(sheet)
}

@Composable
fun CreationAdditionScreenSetup(viewModel: MainViewModel,
                                sheetViewModel: SheetViewModel,
                                navController: NavHostController,
                                artistName: String,
                                songName: String,
                                type: String)
{
    viewModel.findSongByNameAndArtistName(songName, artistName)
    val song by viewModel.song.observeAsState(Song(
        id = 0,
        artists = Artist(id = 0, name= ""),
        avgDuration = 0,
        name = "",
        rawLyrics = ""
    ))

    CreationAdditionalScreen(
        song = song,
        artist = artistName,
        type=type,
        songName = songName,
        mainViewModel = viewModel,
        sheetViewModel = sheetViewModel,
        navController = navController
    )
}

@Composable
fun HomeScreenSetup(viewModel: MainViewModel, navController: NavHostController, songViewModel: SongViewModel, sheetViewModel: SheetViewModel) {

    viewModel.loadSongs(songViewModel)
    viewModel.loadSheets(sheetViewModel)
    viewModel.getCounts()

    HomeScreen(
        viewModel = viewModel,
        navController = navController,
        songViewModel = songViewModel,
        sheetViewModel = sheetViewModel
    )
}

@Composable
fun SearchScreenSetup(viewModel: MainViewModel, navController: NavHostController) {

    val searchResults by viewModel.searchResults.observeAsState(listOf())

    SearchScreen(
        mainViewModel = viewModel,
        searchResult = searchResults,
        navController = navController
    )
}

@Composable
fun SheetScreenSetup(viewModel: MainViewModel, navController: NavHostController, id: Int) {

    viewModel.findSongById(id)
    viewModel.findSheetsForSong(id)
    val sheets by viewModel.allSheets.observeAsState(listOf())
    val searchResult by viewModel.song.observeAsState(Song(
        id = 0,
        artists = Artist(id = 0, name= ""),
        avgDuration = 0,
        name = "",
        rawLyrics = ""
    ))

    SheetScreen(
        mainViewModel = viewModel,
        navController = navController,
        song = searchResult,
        sheets = sheets
    )
}

@Composable
fun ChordScreenSetup(){
    ChordScreen()
}