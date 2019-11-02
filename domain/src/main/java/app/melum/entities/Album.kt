package app.melum.entities

import java.io.Serializable

data class Album(
    val id: String,
    val title: String,
    val cover: String
) : Serializable {
}


