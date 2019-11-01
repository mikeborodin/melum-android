package app.melum.data.network.pojo

import com.google.gson.annotations.SerializedName

data class SearchResult(
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
    val artist: List<SearchedArtist>
)

data class SearchedArtist(
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val streamable: String,
    val url: String
)


data class OpensearchQuery(
    @SerializedName("#text")
    val text: String,
    val role: String,
    val searchTerms: String,
    val startPage: String
)