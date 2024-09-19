package pe.edu.upc.jokescompose.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.jokescompose.domain.Joke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeService {

    @Headers("Accept: application/json")
    @GET("/")
    suspend fun getJoke(): Response<JokeDto>
}