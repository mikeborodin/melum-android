package app.melum.entities

data class PagedResults<T>(
    val data: T,
    val currentPage: Int,
    val maxPage: Int
)