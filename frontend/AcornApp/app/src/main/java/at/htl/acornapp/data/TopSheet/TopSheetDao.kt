package at.htl.acornapp.data.TopSheet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TopSheetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTopSheet(topSheet: TopSheet)

    @Query("SELECT * FROM top_sheet_table")
    fun readAllData(): LiveData<List<TopSheet>>

    @Query("DELETE FROM top_sheet_table " +
            "where top_sheet_access_time_stamp " +
            "NOT IN (" +
            "SELECT top_sheet_access_time_stamp " +
            "from top_sheet_table " +
            "ORDER BY top_sheet_access_time_stamp " +
            "DESC LIMIT 3)"
    )
    fun deleteOldest()

    @Query("DELETE FROM top_sheet_table")
    fun drop()
}