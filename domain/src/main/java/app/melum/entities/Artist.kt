package app.melum.entities

import java.io.Serializable

data class Artist(
    val id: String,
    val name: String,
    val image: String,
    val link: String
) : Serializable {

}


