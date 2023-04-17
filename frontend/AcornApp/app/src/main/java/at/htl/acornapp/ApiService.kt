package at.htl.acornapp

import at.htl.acornapp.data.Artist.Artist
import at.htl.acornapp.data.Sheet.Sheet
import at.htl.acornapp.data.Song.Song
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/artist")
    suspend fun getArtists() : List<Artist>
    @GET("/song")
    suspend fun getSongs(): List<Song>
    @GET("/song/text")
    suspend fun getSongText(
        @Query("artistName") artistName: String,
        @Query("songName") songName: String
    ): String
    @GET("/chord-sheet")
    suspend fun getSheets(
    ):List<Sheet>
    @GET("/song/for")
    suspend fun getSongsForName(
        @Query("name") name: String
    ):List<Song>

    @GET("/chord-sheet/for-id")
    suspend fun getSheetsForId(
        @Query("id") songId: Int
    ): List<Sheet>

    @POST("/chord-sheet")
    suspend fun postSheet(@Body sheet: Sheet): Call<Sheet>

    companion object{
        var apiService: ApiService? = null
        fun getInstance() : ApiService {
            if(apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(BuildConfig.RETROFIT_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }

}