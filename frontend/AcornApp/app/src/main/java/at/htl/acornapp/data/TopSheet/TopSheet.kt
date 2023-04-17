package at.htl.acornapp.data.TopSheet

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Song.Song
import java.sql.Timestamp

@Entity(tableName= "top_sheet_table")
data class TopSheet (
    @Embedded
    @PrimaryKey
    val sheet: Sheet,
    @ColumnInfo(name = "top_sheet_access_time_stamp")
    val tsLong: Long
)