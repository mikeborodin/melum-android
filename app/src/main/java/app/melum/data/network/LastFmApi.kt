package app.melum.data.network

import app.melum.data.network.pojo.AlbumDetailsResponse
import app.melum.data.network.pojo.SearchArtistResponse
import app.melum.data.network.pojo.TopAbumsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApi {

    @GET("?method=artist.search")
    suspend fun searchArtist(@Query("artist") artist: String, @Query("limit") limit: Int = 20): SearchArtistResponse

    @GET("?method=artist.gettopalbums")
    suspend fun getArtistTopAlbums(@Query("artist") artist: String): TopAbumsResponse

    @GET("?method=album.getinfo")
    suspend fun getAlbumInfo(@Query("mbid") id: String): AlbumDetailsResponse?

}