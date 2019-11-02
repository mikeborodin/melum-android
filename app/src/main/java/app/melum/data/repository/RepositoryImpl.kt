package app.melum.data.repository

import app.melum.abstractions.Repository
import app.melum.data.network.LastFmApi
import app.melum.entities.AlbumDetails
import app.melum.entities.Artist
import app.melum.entities.PagedResults

class RepositoryImpl(private val network: LastFmApi) : Repository {

    override suspend fun searchArtists(query: String): PagedResults<List<Artist>> {
        val response = network.searchArtist(query)

        return PagedResults(
            response.results.artistmatches.artist.map { it.toArtistPreview() },
            response.results.startIndex.toIntOrNull() ?: 0,
            response.results.totalResults.toIntOrNull() ?: 0
        )

    }

    override suspend fun getArtistInfo(name: String): PagedResults<List<AlbumDetails>> {
        TODO("not implemented")
    }

    override suspend fun getAlbumInfo(artist: String, album: String): AlbumDetails {
        TODO("not implemented")
    }

    override suspend fun getSavedAlbums(): PagedResults<List<AlbumDetails>> {
        TODO("not implemented")
    }

    override suspend fun saveAlbum(id: String) {
        TODO("not implemented")
    }

}