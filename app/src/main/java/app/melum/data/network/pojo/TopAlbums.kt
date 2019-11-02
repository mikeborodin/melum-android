package app.melum.data.network.pojo

import app.melum.entities.Album
import com.google.gson.annotations.SerializedName

data class TopAbumsResponse(
    val topalbums: Topalbums
)

data class Tags(
    val tag: List<Tag>
)

data class Tag(
    val name: String,
    val url: String
)

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
)


data class Topalbums(
    @SerializedName("@attr")
    val attr: TopalbumsAttr,
    val album: List<AlbumNetwork>
)

data class TopalbumsAttr(
    val artist: String,
    val page: String,
    val perPage: String,
    val total: String,
    val totalPages: String
)

data class AlbumNetwork(
    val artist: Artist,
    val image: List<Image>,
    val mbid: String?,
    val name: String,
    val playcount: Int,
    val url: String
) {
    fun toAlbum(): Album {
        return Album(
            mbid ?: "",
            name,
            image.associate { it.size to it.text }.getOrElse("large", { "" })
        )
    }
}

data class Artist(
    val mbid: String,
    val name: String,
    val url: String
)