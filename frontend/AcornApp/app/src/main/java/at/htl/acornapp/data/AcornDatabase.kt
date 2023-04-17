package at.htl.acornapp.data

import android.content.Context
import androidx.room.*
import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.Artist.ArtistDao
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Sheet.SheetDao
//import at.htl.acornapp.data.Artist.ArtistDao
import at.htl.acornapp.data.Song.Song
import at.htl.acornapp.data.Song.SongDao
import at.htl.acornapp.data.TopSheet.TopSheet
import at.htl.acornapp.data.TopSheet.TopSheetDao
import kotlinx.coroutines.CoroutineScope

@Database(entities = [
    Artist::class,
    Song::class,
    Sheet::class,
    TopSheet::class
                     ],
    version = 15,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AcornDatabase: RoomDatabase() {

    abstract fun artistDao(): ArtistDao
    abstract fun songDao(): SongDao
    abstract fun sheetDao(): SheetDao
    abstract fun topSheetDao(): TopSheetDao

    companion object {
        @Volatile
        private var INSTANCE:AcornDatabase? = null

        fun getDatabase(context: Context):AcornDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AcornDatabase::class.java,
                        "acorn_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
