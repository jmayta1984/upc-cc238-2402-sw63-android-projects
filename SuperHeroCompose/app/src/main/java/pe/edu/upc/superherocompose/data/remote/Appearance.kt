package pe.edu.upc.superherocompose.data.remote

data class Appearance(
    val gender: String,
    val height: List<String>,
    val race: String,
    val weight: List<String>
)