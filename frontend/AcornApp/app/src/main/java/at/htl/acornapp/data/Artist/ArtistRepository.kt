package at.htl.acornapp.data.Artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import at.htl.acornapp.data.Song.Song
import kotlinx.coroutines.*

class ArtistRepository(private val artistDao: ArtistDao) {
    val readAllData: LiveData<List<Artist>> = artistDao.readAllData()
    val searchResult = MutableLiveData<Artist>();
    val count = MutableLiveData<Int>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addArtist(artist: Artist) {
        coroutineScope.launch(Dispatchers.IO) {
            artistDao.addArtist(artist)
        }
    }

    fun getArtistCount() {
        coroutineScope.launch(Dispatchers.Main) {
            count.value = asyncCount().await()
        }
    }

    fun findByName(name: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFindByName(name).await()
        }
    }

    private fun asyncFindByName(name: String): Deferred<Artist?> =
        coroutineScope.async(Dispatchers.IO){
            return@async artistDao.findByName(name)
        }

    private fun asyncCount(): Deferred<Int?> =
        coroutineScope.async(Dispatchers.IO){
            return@async artistDao.getCount()
        }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO){
            artistDao.drop()
        }
    }
}