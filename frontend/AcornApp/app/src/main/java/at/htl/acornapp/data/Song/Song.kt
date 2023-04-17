package at.htl.acornapp.data.Song

import androidx.room.*
import at.htl.acornapp.data.Artist.Artist

@Entity(tableName = "song_table")
data class Song (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "song_id")
    val id: Int,
    @ColumnInfo(name = "song_name")
    val name: String,

    @Embedded
    val artists: Artist,
    @ColumnInfo(name = "song_lyrics")
    val rawLyrics: String,
    @ColumnInfo(name = "song_duration")
    val avgDuration: Int
)