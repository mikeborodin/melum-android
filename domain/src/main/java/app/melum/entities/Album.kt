package app.melum.entities

import java.io.Serializable

data class Album(
    val id: String,
    val title: String,
    val cover: String,
    var songs: MutableList<Song> = mutableListOf(),
    var artist: Artist? = null,
    val description: String = ""
    ) : Serializable


