package app.melum.data.database.pojo

import app.melum.entities.Artist
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmArtist(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var image: String = "",
    var link: String = ""
) : RealmObject() {

    fun toArtist() = Artist(id, name, image, link)

    companion object {
        fun fromArtist(artist: Artist) = RealmArtist(
            artist.id,
            artist.name,
            artist.image,
            artist.link
        )
    }
}