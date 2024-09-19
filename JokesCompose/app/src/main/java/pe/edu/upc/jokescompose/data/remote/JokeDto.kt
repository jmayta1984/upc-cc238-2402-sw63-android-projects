package pe.edu.upc.jokescompose.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.jokescompose.domain.Joke

data class JokeDto(
    val id: String,
    @SerializedName("joke")
    val description: String,
    val status: Int
)

fun JokeDto.toJoke() = Joke(description)
