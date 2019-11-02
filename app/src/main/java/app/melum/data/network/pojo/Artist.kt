package app.melum.data.network.pojo

import app.melum.entities.Artist
import com.google.gson.annotations.SerializedName

data class SearchArtistResponse(
    val results: Results
)

data class Results(
    @SerializedName("@attr")
    val attr: For,
    val artistmatches: ArtistMatches,

    @SerializedName("opensearch:Query")
    val query: OpensearchQuery,
    @SerializedName("opensearch:itemsPerPage")
    val itemsPerPage: String,
    @SerializedName("opensearch:startIndex")
    val startIndex: String,
    @SerializedName("opensearch:totalResults")
    val totalResults: String
)

data class For(
    val `for`: String
)

data class ArtistMatches(
    val artist: List<ArtistNetwork>
)

data class ArtistNetwork(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String?
) {
    fun toArtist(): Artist {
        return Artist(mbid, name,
            image.associate { it.size to it.text }.getOrElse("medium", { "" }),
            url ?: ""
        )
    }
}


data class OpensearchQuery(
    @SerializedName("#text")
    val text: String,
    val role: String,
    val searchTerms: String,
    val startPage: String
)