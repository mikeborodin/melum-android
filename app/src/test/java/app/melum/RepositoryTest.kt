package app.melum

import app.melum.abstractions.Repository
import app.melum.data.database.AlbumsDatabase
import app.melum.data.network.LastFmApi
import app.melum.data.network.pojo.*
import app.melum.data.repository.RepositoryImpl
import app.melum.entities.Artist
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryTest {

    @Test
    fun `repository should call api`() {
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
            assert(list[0].name == artist) { "Repository result should be not empty" }
        }
    }

    fun generateFakeDataForArtist(artist: String) = SearchArtistResponse(
        Results(
            For("aaa"),
            ArtistMatches(
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
}
