package app.melum.abstractions

import app.melum.entities.AlbumDetails
import app.melum.entities.Artist
import app.melum.entities.PagedResults

interface Repository {

    suspend fun searchArtists(query: String): PagedResults<List<Artist>>

    suspend fun getArtistInfo(name: String): PagedResults<List<AlbumDetails>>

    suspend fun getAlbumInfo(artist: String, album: String): AlbumDetails

    suspend fun getSavedAlbums(): PagedResults<List<AlbumDetails>>

    suspend fun saveAlbum(id: String)

}