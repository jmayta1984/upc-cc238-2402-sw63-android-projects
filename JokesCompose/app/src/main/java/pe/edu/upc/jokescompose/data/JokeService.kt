package pe.edu.upc.jokescompose.data

import com.google.gson.annotations.SerializedName
import pe.edu.upc.jokescompose.domain.Joke
import retrofit2.Response
import retrofit2.http.GET

interface JokeService {

    @GET("/")
    suspend fun getJoke(): Response<JokeDto>
}

data class JokeDto(
    val id: String,
    @SerializedName("joke")
    val description: String,
    val status: Int
)

fun JokeDto.toJoke() = Joke(description)