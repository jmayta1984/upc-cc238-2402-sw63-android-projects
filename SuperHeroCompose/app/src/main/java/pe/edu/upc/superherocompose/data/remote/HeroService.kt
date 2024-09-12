package pe.edu.upc.superherocompose.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface HeroService {

    @GET("f274286a22873ec9fc7a5782940f7ca2/search/{name}")
    suspend fun searchHero(@Path("name") name: String): Response<ResponseDto>
}