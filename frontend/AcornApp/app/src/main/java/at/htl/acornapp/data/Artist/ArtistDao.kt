package at.htl.acornapp.data.Artist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArtist(artist: Artist)

    @Query("SELECT * FROM artist_table ORDER BY artist_id ASC")
    fun readAllData(): LiveData<List<Artist>>

    @Query("SELECT * FROM artist_table WHERE artist_name LIKE :name")
    fun findByName(name: String): Artist

    @Query("SELECT COUNT(*) FROM artist_table")
    fun getCount(): Int

    @Query("DELETE FROM artist_table")
    fun drop()
}