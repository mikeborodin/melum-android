package app.melum.data.repository

import app.melum.abstractions.Repository
import app.melum.data.network.LastFmApi
import app.melum.entities.Album
import app.melum.entities.AlbumDetails
import app.melum.entities.Artist
import app.melum.entities.PagedResults

const val LIMIT = 30

class RepositoryImpl(private val network: LastFmApi) : Repository {

    override suspend fun searchArtists(query: String): PagedResults<List<Artist>> {
        val response = network.searchArtist(query)

        return PagedResults(
            response.results.artistmatches.artist.map { it.toArtist() },
            response.results.startIndex.toIntOrNull() ?: 0,
            response.results.totalResults.toIntOrNull() ?: 0
        )

    }

    override suspend fun getArtistTopAlbums(name: String): PagedResults<List<Album>> {
        val response = network.getArtistTopAlbums(name)
        return PagedResults(
            response.topalbums.album.map { it.toAlbum() }, 0, 0
        )
    }

    override suspend fun getAlbumInfo(id: String): AlbumDetails {
        val response = network.getAlbumInfo(id)
        return response.album.toAlbumDetails()
    }

    override suspend fun getSavedAlbums(): PagedResults<List<Album>> {
        TODO("not implemented")
    }

    override suspend fun saveAlbum(id: String) {
        TODO("not implemented")
    }

}