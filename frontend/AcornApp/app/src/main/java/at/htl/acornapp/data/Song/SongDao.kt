package at.htl.acornapp.data.Song

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface SongDao {
    @Insert(onConflict = REPLACE)
    fun addSong(vararg song: Song)

    @Query("SELECT * FROM song_table ORDER BY song_name ASC")
    fun readAllData(): LiveData<List<Song>>

    @Query("SELECT * FROM song_table WHERE song_name LIKE '%'||:name||'%' OR artist_name LIKE '%'||:name||'%'")
    fun findSong(name: String): List<Song>

    @Query("SELECT * FROM song_table WHERE song_id is :id")
    fun findSongById(id: Int): Song

    @Query("SELECT * FROM song_table WHERE song_name is :name and artist_name is :artist")
    fun findSongByNameAndArtist(name: String, artist: String): Song

    @Query("SELECT COUNT(*) FROM song_table")
    fun getCount(): Int

    @Query("DELETE FROM song_table")
    fun drop()

}