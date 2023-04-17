package at.htl.acornapp.data.Sheet

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.htl.acornapp.ApiService
import at.htl.acornapp.data.Song.Song
import kotlinx.coroutines.launch

class SheetViewModel: ViewModel() {

    var sheetListResponse:List<Sheet> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getSheetList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val sheetList = apiService.getSheets()
                sheetListResponse = sheetList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
    }

    fun postSheet(sheet: Sheet){
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try{
                apiService.postSheet(sheet)
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Log.e("Error ", errorMessage)
            }
        }
    }

}