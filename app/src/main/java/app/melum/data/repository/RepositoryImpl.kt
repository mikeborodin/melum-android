package app.melum.data.repository

import app.melum.abstractions.Repository
import app.melum.data.database.AlbumsDatabase
import app.melum.data.network.LastFmApi
import app.melum.entities.Album
import app.melum.entities.Artist


open class RepositoryImpl(private val network: LastFmApi, private val db: AlbumsDatabase) :
    Repository {

    override suspend fun searchArtists(query: String): List<Artist> {
        val response = network.searchArtist(query, 20)
        return response.results.artistmatches.artist.map { it.toArtist() }
    }

    override suspend fun getArtistTopAlbums(name: String): List<Album> {
        val response = network.getArtistTopAlbums(name)
        return response.topalbums.album
            //sometimes lastFM returns corrupted albums, so we filter them out
            .filter { it.name != "(null)" }
            .map { it.toAlbum() }

    }

    override suspend fun getAlbumInfo(id: String): Album {
        val response = network.getAlbumInfo(id)
        return response?.album?.toAlbum() ?: throw Exception("Problem while loading album")
    }

    override suspend fun getSavedAlbums(): List<Album> {
        return db.getSavedAlbums()
    }

    override suspend fun saveAlbum(album: Album) {
        check(album.songs.isNotEmpty()) { "Can not save empty album" }
        db.saveAlbum(album)
    }

    override suspend fun deleteSavedAlbum(id: String) {
        db.deleteSavedAlbum(id)
    }

}