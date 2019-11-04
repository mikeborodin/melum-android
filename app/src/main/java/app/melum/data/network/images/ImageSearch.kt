package app.melum.data.network.images


class ImageSearch(
    private val mb: MusicbrainzApi,
    private val wiki: WikiCommonsSearchApi
) {

    suspend fun getImageUrl(q: String): String {
        return runCatching {
            val xml = mb.artistInfo(q).string()

            val wikiHost = "https://commons.wikimedia.org/wiki/"
            val wikiUrl = "(?<=${wikiHost}).*?(?=<)".toRegex().find(xml)!!.value

            val start = "https://upload.wikimedia.org/wikipedia"
            val wikiPage = wiki.search(wikiUrl).string()
            val url = "(?<=${start}).*?(?=\")".toRegex().find(wikiPage)!!.value

            val prefix = "https://upload.wikimedia.org/wikipedia"
            prefix + url
        }.getOrElse { "" }
    }
}