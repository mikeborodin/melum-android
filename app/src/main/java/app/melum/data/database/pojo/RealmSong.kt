package app.melum.data.database.pojo

import app.melum.entities.Song
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmSong(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var stream: String = ""
) :
    RealmObject() {

    fun toSong() = Song(name, stream)

    companion object {
        fun fromSong(song: Song) = RealmSong("", song.name, song.stream)
    }

}