package app.melum.entities

data class Artist(val name: String, val image: String) {

}


data class PagedResults<T>(val data: T, val currentPage: Int, val maxPage: Int)
