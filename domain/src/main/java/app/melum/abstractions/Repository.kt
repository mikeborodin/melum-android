package app.melum.abstractions

import app.melum.entities.Album
import app.melum.entities.AlbumDetails
import app.melum.entities.Artist

interface Repository {

    suspend fun searchArtists(query: String): List<Artist>

    suspend fun getArtistTopAlbums(name: String): List<Album>

    suspend fun getSavedAlbums(): List<Album>

    suspend fun getAlbumInfo(id: String): AlbumDetails

    suspend fun saveAlbum(album: Album)

    suspend fun deleteSavedAlbum(id: String)

}