package app.melum.data.network

import app.melum.data.network.pojo.AlbumResponse
import app.melum.data.network.pojo.ArtistResponse
import app.melum.data.network.pojo.SearchArtistResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET("?method=artist.search")
    suspend fun searchArtist(@Query("artist") artist: String): SearchArtistResponse

    @GET("?method=artist.gettopalbums")
    suspend fun getArtistTopAlbums(@Query("artist") artist: String): ArtistResponse

    @GET("?method=album.getinfo")
    suspend fun getAlbumInfo(
        @Query("artist") artist: String,
        @Query("album") album: String
    ): AlbumResponse

}