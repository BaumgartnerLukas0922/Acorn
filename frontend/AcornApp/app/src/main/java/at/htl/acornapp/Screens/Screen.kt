package at.htl.acornapp.Screens

const val ARTIST_NAME_ARGUMENT = "artist_name"
const val SONG_NAME_ARGUMENT = "song_name"
const val SHEET_ID_ARGUMENT = "sheet_id"
const val TYPE_ARGUMENT = "type"

sealed class Screen(val route: String){
    object Home: Screen(route = "home_screen")
    object Splash: Screen(route = "splash")
    object Create: Screen(route = "create_screen")
    object Search: Screen(route = "search_screen")
    object Chords: Screen(route = "chords_screen")
    object CreateAddit: Screen(
        route = "create_additional_screen/{$ARTIST_NAME_ARGUMENT}/{$SONG_NAME_ARGUMENT}/{$TYPE_ARGUMENT}"
    ){
        fun passArtistAndSongName(
            artist: String,
            song: String,
            type: String
        ): String{
            return "create_additional_screen/$artist/$song/$type"
        }
    }
    object Sheet: Screen(route = "sheet_screen/{$SHEET_ID_ARGUMENT}"){
        fun passSheetId(id: Int): String{
            return this.route.replace(
                oldValue = "{$SHEET_ID_ARGUMENT}",
                newValue = id.toString()
            )
        }
    }

    object SheetDetail: Screen(route = "sheet_detail/{$SHEET_ID_ARGUMENT}"){
        fun passSheetId(id: Int): String{
            return this.route.replace(
                oldValue = "{$SHEET_ID_ARGUMENT}",
                newValue = id.toString()
            )
        }
    }
}
