package at.htl.acornapp.data.Sheet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import at.htl.acornapp.data.Song.Song

@Dao
interface SheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSheet(sheet: Sheet)

    @Query("SELECT * FROM sheet_table")
    fun readAllData(): LiveData<List<Sheet>>

    @Query("SELECT * FROM sheet_table WHERE song_id IS :songId")
    fun getSheetsForSongID(songId: Long): List<Sheet>

    @Query("SELECT * FROM sheet_table WHERE sheet_table.sheet_id IS :Id")
    fun getSheetById(Id: Int): Sheet

    @Query("SELECT COUNT(*) FROM sheet_table")
    fun getCount(): Int

    @Query("DELETE FROM sheet_table")
    fun deleteAll()
}