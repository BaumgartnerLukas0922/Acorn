package at.htl.acornapp.Navigation

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.htl.acornapp.data.AcornDatabase
import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.Artist.ArtistRepository
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetRepository
import at.htl.acornapp.data.Sheet.SheetViewModel
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Song.SongRepository
import at.htl.acornapp.data.Song.SongViewModel
import at.htl.acornapp.data.TopSheet.TopSheet
import at.htl.acornapp.data.TopSheet.TopSheetRepository
import at.htl.acornapp.data.Type

class MainViewModel(application: Application) : ViewModel() {

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState){
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String){
        _searchTextState.value = newValue
    }

    val allArtists: LiveData<List<Artist>>
    var allSongs: LiveData<List<Song>>
    var allSheets: LiveData<List<Sheet>>
    var song: MutableLiveData<Song>
    var artist: MutableLiveData<Artist>
    var sheet: MutableLiveData<Sheet>
    var topSheets: LiveData<List<TopSheet>>
    val artistCount: MutableLiveData<Int>
    val songCount: MutableLiveData<Int>
    val sheetCount: MutableLiveData<Int>
    private val artistRepository: ArtistRepository
    private val songRepository: SongRepository
    private val sheetRepository: SheetRepository
    private val topSheetRepository: TopSheetRepository
    val searchResults: MutableLiveData<List<Song>>

    init{
        val acornDb = AcornDatabase.getDatabase(application)
        val artistDao = acornDb.artistDao()
        val songDao = acornDb.songDao()
        val sheetDao = acornDb.sheetDao()
        val topSheetDao = acornDb.topSheetDao()

        artistRepository = ArtistRepository(artistDao)
        songRepository = SongRepository(songDao)
        sheetRepository = SheetRepository(sheetDao)
        topSheetRepository = TopSheetRepository(topSheetDao)

        allSongs = songRepository.allSongs
        song = songRepository.searchResult
        artist = artistRepository.searchResult
        sheet = sheetRepository.searchResult
        searchResults = songRepository.searchResults
        topSheets = topSheetRepository.allTopSheets
        allArtists = artistRepository.readAllData
        allSheets = sheetRepository.allSheets
        artistCount = artistRepository.count
        sheetCount = sheetRepository.count
        songCount = songRepository.count

        /*sheetRepository.deleteSheets()
        songRepository.deleteAll()
        artistRepository.deleteAll()

        artistRepository.addArtist(Artist(
            name="The Lumineers",
            id = 1))
        songRepository.addSong(Song(
            artists = Artist(
                name="The Lumineers",
                id = 1),
            avgDuration = 140,
            rawLyrics = "You're gonna leave\n" +
                    "It ain't gonna break my heart, mama\n" +
                    "I never see nobody quite like you\n" +
                    "And if you ever change your tune\n" +
                    "Oh, the world's got the best of you\n" +
                    "You can always find me where the skies are blue\n" +
                    "\n" +
                    "Wanting to change, turning to what you love, mama\n" +
                    "And I would have gave all of my best to you\n" +
                    "So, if you ever need a fool,\n" +
                    "Who will give you a love so true\n" +
                    "You can always find me where the skies are blue\n" +
                    "\n" +
                    "Lay your dreams little darlin' in a flower bed\n" +
                    "Let that sunshine in your hair\n" +
                    "In your hair\n" +
                    "\n" +
                    "You're gonna bleed somebody's brand new love, mama\n" +
                    "Who's gonna feel you like it always do\n" +
                    "And when your fairytale is through\n" +
                    "And you're looking for someone new\n" +
                    "You can always find me where the skies are blue\n" +
                    "You can always find me where the skies are blue",
            name = "Where The Skies Are Blue",
            id = 1
        ))
        sheetRepository.addSheet(
            Sheet(
                song = Song(
                    artists = Artist(
                        name="The Lumineers",
                        id = 1),
                    avgDuration = 140,
                    rawLyrics = "You're gonna leave\n" +
                            "It ain't gonna break my heart, mama\n" +
                            "I never see nobody quite like you\n" +
                            "And if you ever change your tune\n" +
                            "Oh, the world's got the best of you\n" +
                            "You can always find me where the skies are blue\n" +
                            "\n" +
                            "Wanting to change, turning to what you love, mama\n" +
                            "And I would have gave all of my best to you\n" +
                            "So, if you ever need a fool,\n" +
                            "Who will give you a love so true\n" +
                            "You can always find me where the skies are blue\n" +
                            "\n" +
                            "Lay your dreams little darlin' in a flower bed\n" +
                            "Let that sunshine in your hair\n" +
                            "In your hair\n" +
                            "\n" +
                            "You're gonna bleed somebody's brand new love, mama\n" +
                            "Who's gonna feel you like it always do\n" +
                            "And when your fairytale is through\n" +
                            "And you're looking for someone new\n" +
                            "You can always find me where the skies are blue\n" +
                            "You can always find me where the skies are blue",
                    name = "Where The Skies Are Blue",
                    id = 1
                ),
                chords = "A,Bm,E,C#m,F#m,B,D,E7,B7",
                sheet = "[Intro]\n" +
                        "%A  Bm  A  E\n" +
                        "\n" +
                        "[Verse 1]\n" +
                        "%A\n" +
                        "You're gonna leave\n" +
                        "%                        Bm\n" +
                        "It ain't gonna break my heart, mama\n" +
                        "%A                             Bm\n" +
                        "I never see nobody quite like you\n" +
                        "%           C#m              F#m\n" +
                        "And if you ever change your tune\n" +
                        "%        B\n" +
                        "Oh, the world's got the best of you\n" +
                        "%    D                  E                   A     E7\n" +
                        "You can always find me where the skies are blue\n" +
                        "\n" +
                        "[Verse 2]\n" +
                        "%A                                      Bm\n" +
                        "Wanting to change, turning to what you love, mama\n" +
                        "%A                                       Bm\n" +
                        "And I would have gave all of my best to you\n" +
                        "%      C#m              F#m\n" +
                        "So, if you ever need a fool,\n" +
                        "%         B\n" +
                        "Who will give you a love so true\n" +
                        "%    D                  E                   A\n" +
                        "You can always find me where the skies are blue\n" +
                        "\n" +
                        "[Bridge]\n" +
                        "%D        A             E            D      A\n" +
                        "Lay your dreams little darlin' in a flower bed\n" +
                        "%F#m D        A                E\n" +
                        "Let that sunshine in your hair\n" +
                        "%\n" +
                        "In your hair\n" +
                        "\n" +
                        "[Verse 3]\n" +
                        "%A                                       Bm\n" +
                        "You're gonna bleed somebody's brand new love, mama\n" +
                        "%A                                   Bm\n" +
                        "Who's gonna feel you like it always do\n" +
                        "%              C#m          F#m\n" +
                        "And when your fairytale is through\n" +
                        "%           B\n" +
                        "And you're looking for someone new\n" +
                        "%    D                  E                   F#m    B7\n" +
                        "You can always find me where the skies are blue\n" +
                        "%    D                  E                   A\n" +
                        "You can always find me where the skies are blue",
                type = Type.CHORDS,
                id = 1
            ))*/
    }

    fun insertSong(song: Song){
        insertArtist(song.artists)
        songRepository.addSong(song)
        allSongs = songRepository.allSongs
    }

    fun insertSheet(sheet: Sheet){
        insertSong(sheet.song)
        sheetRepository.addSheet(sheet)
    }

    fun insertArtist(artist: Artist){
        artistRepository.addArtist(artist)
    }

    fun getCounts(){
        songRepository.getSongCount()
        artistRepository.getArtistCount()
        sheetRepository.getSheetCount()
    }

    fun loadSongs(songViewModel: SongViewModel){
        songViewModel.getSongList()
        songViewModel.songListResponse.forEach {
            s -> insertSong(s)
        }
    }

    fun postSheet(sheetViewModel: SheetViewModel, sheet: Sheet){
        sheetViewModel.postSheet(sheet)
    }

    fun loadSheets(sheetViewModel: SheetViewModel) {
        sheetViewModel.getSheetList()
        sheetViewModel.sheetListResponse.forEach { s ->
            insertSheet(s)
        }
    }

    fun findSong(name: String){
        songRepository.findSong(name)
    }

    fun findSongById(id: Int){
        songRepository.findSingleSong(id)
    }
    fun findSongByNameAndArtistName(name: String, artist: String){
        songRepository.findSingleSong(name, artist)
    }
    fun findSheetsForSong(id: Int){
        sheetRepository.findSheet(id.toLong())
    }

    fun findSheet(id: Int){
        sheetRepository.findSheetById(id)
    }

    fun deleteAll() {
        topSheetRepository.deleteAll()
        sheetRepository.deleteSheets()
        songRepository.deleteAll()
        artistRepository.deleteAll()
    }

    fun addToTopSheets(sheet: Sheet){
        topSheetRepository.addSheet(sheet)
    }

    fun getTopSheets(){
        topSheets = topSheetRepository.allTopSheets;
    }
}