package at.htl.acornapp.data.Song

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.htl.acornapp.ApiService
import at.htl.acornapp.data.AcornDatabase
import at.htl.acornapp.data.Artist.Artist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SongViewModel: ViewModel() {

    var songListResponse:List<Song> by mutableStateOf(listOf())
    var songText: String by mutableStateOf("")
    var errorMessage: String by mutableStateOf("")

    fun getSongList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val songList = apiService.getSongs()
                songListResponse = songList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
    }

    fun getSongList(name: String) {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val songList = apiService.getSongsForName(name)
                Log.v("", songList[0].toString())
                songListResponse = songList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
    }

    fun getSongText(artistName: String, songName: String): String{
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val rawText = apiService.getSongText(artistName, songName)
                Log.v("", rawText)
                songText = rawText
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
        return songText
    }
}