package at.htl.acornapp.data

import androidx.room.TypeConverter
import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.Song.Song
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(value: Artist?) = Gson().toJson(value);

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Artist::class.java)

    @TypeConverter
    fun songToJson(value: Song?) = Gson().toJson(value);

    @TypeConverter
    fun jsonToSong(value: String) = Gson().fromJson(value, Song::class.java)
}