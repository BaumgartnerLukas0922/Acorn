package at.htl.acornapp.data.Sheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Song.SongDao
import kotlinx.coroutines.*

class SheetRepository(private val sheetDao: SheetDao) {

    val allSheets: LiveData<List<Sheet>> = sheetDao.readAllData()
    val searchResults = MutableLiveData<List<Sheet>>();
    val searchResult = MutableLiveData<Sheet>();
    val count = MutableLiveData<Int>();
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addSheet(newSheet: Sheet){
        coroutineScope.launch(Dispatchers.IO){
            sheetDao.addSheet(newSheet)
        }
    }

    fun findSheet(id: Long){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Long): Deferred<List<Sheet>?> =
        coroutineScope.async(Dispatchers.IO){
            return@async sheetDao.getSheetsForSongID(id)
        }

    fun findSheetById(id: Int){
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Int): Deferred<Sheet?> =
        coroutineScope.async(Dispatchers.IO){
            return@async sheetDao.getSheetById(id)
        }

    fun getSheetCount() {
        coroutineScope.launch(Dispatchers.Main) {
            count.value = asyncCount().await()
        }
    }
    private fun asyncCount(): Deferred<Int?> =
        coroutineScope.async(Dispatchers.IO){
            return@async sheetDao.getCount()
        }

    fun deleteSheets(){
        coroutineScope.launch(Dispatchers.IO){
            sheetDao.deleteAll()
        }
    }
}