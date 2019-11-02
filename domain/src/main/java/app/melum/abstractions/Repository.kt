package app.melum.abstractions

import app.melum.entities.Album
import app.melum.entities.AlbumDetails
import app.melum.entities.Artist
import app.melum.entities.PagedResults

interface Repository {

    suspend fun searchArtists(query: String): PagedResults<List<Artist>>

    suspend fun getArtistTopAlbums(name: String): PagedResults<List<Album>>

    suspend fun getSavedAlbums(): PagedResults<List<Album>>

    suspend fun getAlbumInfo(id: String): AlbumDetails

    suspend fun saveAlbum(id: String)

}