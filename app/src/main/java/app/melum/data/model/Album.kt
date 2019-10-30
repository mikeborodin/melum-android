package app.melum.data.model

import com.google.gson.annotations.SerializedName

data class Album(
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val tags: Tags,
    val tracks: Tracks,
    val url: String,
    val wiki: Wiki
)

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
)

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