package at.htl.acornapp.data.Sheet

import androidx.room.*
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Type

@Entity(tableName= "sheet_table")
data class Sheet (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sheet_id")
    val id: Int,
    @Embedded
    val song: Song,
    @ColumnInfo(name = "sheet_lyrics")
    val sheet: String,
    @ColumnInfo(name = "sheet_chords")
    val chords: String,
    @ColumnInfo(name= "sheet_type")
    val type: Type
)