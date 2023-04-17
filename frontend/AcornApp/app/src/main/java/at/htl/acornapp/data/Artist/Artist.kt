package at.htl.acornapp.data.Artist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName= "artist_table")
data class Artist(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "artist_id")
    val id: Int,
    @ColumnInfo(name = "artist_name")
    val name: String
)