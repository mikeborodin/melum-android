package app.melum.data.database.pojo

import app.melum.entities.Album
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmAlbum(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var cover: String = "",
    var songs: RealmList<RealmSong> = RealmList(),
    var artist: RealmArtist? = null
) : RealmObject() {

    fun toAlbum() =
        Album(id, title, cover, songs.map { it.toSong() }.toMutableList(), artist?.toArtist())

    companion object {
        fun fromAlbum(album: Album) = RealmAlbum(
            album.id,
            album.title,
            album.cover,
            RealmList<RealmSong>().apply {
                addAll(album.songs.mapIndexed { i, s ->
                    RealmSong.fromSong(s).apply { id = album.id + i }
                })
            },
            album.artist?.let { RealmArtist.fromArtist(it) }
        )
    }
}