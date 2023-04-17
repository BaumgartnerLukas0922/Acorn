package at.htl.acornapp.data.Song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import at.htl.acornapp.data.Artist.Artist
import kotlinx.coroutines.*

class SongRepository(private val songDao: SongDao) {
    val allSongs: LiveData<List<Song>> = songDao.readAllData()
    val searchResults = MutableLiveData<List<Song>>();
    val searchResult = MutableLiveData<Song>();
    val count = MutableLiveData<Int>();
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addSong(song: Song) {
        coroutineScope.launch(Dispatchers.IO) {
            songDao.addSong(song)
        }
    }

    fun findSong(name: String){
        if(!name.equals("")) {
            coroutineScope.launch(Dispatchers.Main) {
                searchResults.value = asyncFind(name).await()
            }
        } else{
            coroutineScope.launch(Dispatchers.Main) {
                searchResults.value = asyncFind("########").await()
            }
        }
    }

    private fun asyncFind(name: String): Deferred<List<Song>?> =
        coroutineScope.async(Dispatchers.IO){
            return@async songDao.findSong(name)
        }

    fun findSingleSong(id: Int){
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFindById(id).await()
        }
    }

    private fun asyncFindById(id: Int): Deferred<Song?> =
        coroutineScope.async(Dispatchers.IO){
            return@async songDao.findSongById(id)
        }

    fun findSingleSong(name: String, artist: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResult.value = asyncFindByNameAndArtist(name, artist).await()
        }
    }

    private fun asyncFindByNameAndArtist(name: String, artist: String): Deferred<Song?> =
        coroutineScope.async(Dispatchers.IO){
            return@async songDao.findSongByNameAndArtist(name, artist)
        }

    fun getSongCount() {
        coroutineScope.launch(Dispatchers.Main) {
            count.value = asyncCount().await()
        }
    }
    private fun asyncCount(): Deferred<Int?> =
        coroutineScope.async(Dispatchers.IO){
            return@async songDao.getCount()
        }

    fun deleteAll(){
        coroutineScope.launch(Dispatchers.IO){
            songDao.drop()
        }
    }

}