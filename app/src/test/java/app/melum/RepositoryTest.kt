package app.melum

import app.melum.abstractions.Repository
import app.melum.data.database.AlbumsDatabase
import app.melum.data.network.LastFmApi
import app.melum.data.network.pojo.*
import app.melum.data.repository.RepositoryImpl
import app.melum.entities.Album
import app.melum.entities.Artist
import app.melum.entities.Song
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryTest {

    @Test
    fun repository_shouldCallApi() {
        val artist = "Metallica"
        runBlocking {
            val db: AlbumsDatabase = mockk()
            val network: LastFmApi = mockk()
            every {
                runBlocking {
                    network.searchArtist(
                        artist,
                        20
                    )
                }
            } returns generateFakeDataForArtist(artist)

            val repo: Repository = RepositoryImpl(network, db)
            val list: List<Artist> = repo.searchArtists(artist)

            assert(list.size == 1) { "Repository result should be not empty" }
            assert(list[0].name == artist) { "Repository result should be relevant" }
        }
    }


    @Test
    fun repository_shouldCallDb() {

        runBlocking {
            val db: AlbumsDatabase = mockk()
            val network: LastFmApi = mockk()
            every {
                runBlocking {
                    db.getSavedAlbums()
                }
            } returns generateFakeSavedDataAlbums()

            val repo: Repository = RepositoryImpl(network, db)
            val list: List<Album> = repo.getSavedAlbums()

            assert(list.size == 1 && list.first().title == "Album Test") { "Results should be the same" }
        }
    }


    @Test
    fun repository_shouldFilterOutErroneousData() {

        val artist = "Metallica"
        runBlocking {
            val db: AlbumsDatabase = mockk()
            val network: LastFmApi = mockk()
            every {
                runBlocking {
                    network.getArtistTopAlbums(artist)
                }
            } returns generateFakeErrorDataForArtist()

            val repo: Repository = RepositoryImpl(network, db)
            val list: List<Album> = repo.getArtistTopAlbums(artist)

            assert(list.isEmpty()) { "Repository result filter out error data" }
        }
    }


    private fun generateFakeSavedDataAlbums(): List<Album> {
        return listOf(
            Album("id", "Album Test", "image", mutableListOf(Song("song", "stream")))
        )
    }

    private fun generateFakeDataForArtist(artist: String) = SearchArtistResponse(
        Results(
            For("aaa"), ArtistMatches(
                listOf(
                    ArtistNetwork(
                        listOf(Image("medium", "medium")),
                        "",
                        "id",
                        artist,
                        "stream",
                        "url"
                    )
                )
            ),
            OpensearchQuery("test", "test", "test", "1"),
            "12", "123", "123"
        )
    )

    private fun generateFakeErrorDataForArtist() = TopAbumsResponse(
        Topalbums(
            TopalbumsAttr((""), "", "", "", ""), listOf(
                AlbumNetwork(
                    Artist("id", "name", "url"),
                    listOf(),
                    "(null)",
                    "(null)",
                    123,
                    "url"
                )
            )
        )
    )
}
