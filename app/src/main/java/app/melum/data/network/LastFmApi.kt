package app.melum.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET("method=artist.search")
    suspend fun searchArtist(@Query("artist") artist: String)

    @GET("method=artist.gettopalbums")
    suspend fun getArtistTopAlbums(@Query("artist") artist: String)

    @GET("method=album.getinfo")
    suspend fun getAlbumInfo(@Query("artist") artist: String,
                             @Query("album") album: String)

}