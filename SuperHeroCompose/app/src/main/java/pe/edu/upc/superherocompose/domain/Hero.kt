package pe.edu.upc.superherocompose.domain

data class Hero(
    val id: String,
    val name: String,
    val fullName: String,
    val poster: String,
    var isFavorite: Boolean = false
)
