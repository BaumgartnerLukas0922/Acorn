package at.htl.acornapp.data.Artist

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.htl.acornapp.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistViewModel: ViewModel() {

    var artistListResponse:List<Artist> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getArtistList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val artistList = apiService.getArtists()
                artistListResponse = artistList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
    }
}