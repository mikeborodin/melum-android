package app.melum.data.network.pojo

import app.melum.entities.Album
import app.melum.entities.Song
import com.google.gson.annotations.SerializedName


class AlbumDetailsResponse(val album: AlbumDetailsNetwork)

data class AlbumDetailsNetwork(
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val tags: Tags,
    val tracks: Tracks,
    val url: String,
    val wiki: Wiki?
) {

    fun toAlbum(): Album {
        return Album(
            mbid,
            name,
            image.associate { it.size to it.text }.getOrElse("medium", { "" }),
            tracks.track.map { it.toSong() }.toMutableList(),
            null, wiki?.content ?: ""
        )
    }
}

data class Tracks(
    val track: List<Track>
)

data class Track(
    @SerializedName("@attr")
    val attr: Attr,
    val artist: ArtistSmall,
    val duration: String,
    val name: String,
    val streamable: Streamable,
    val url: String
) {
    fun toSong() = Song(name, url)
}

data class Attr(
    val rank: String
)

data class ArtistSmall(
    val mbid: String,
    val name: String,
    val url: String
)

data class Streamable(
    @SerializedName("#text")
    val text: String,
    val fulltrack: String
)

data class Wiki(
    val content: String,
    val published: String,
    val summary: String
)