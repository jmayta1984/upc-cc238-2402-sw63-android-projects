package pe.edu.upc.superherocompose.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.superherocompose.domain.Hero

data class HeroDto(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    @SerializedName("powerstats")
    val powerStats: PowerStats,
    val work: Work
)

fun HeroDto.toHero() = Hero(id, name, biography.fullName, image.url)