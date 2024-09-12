package pe.edu.upc.superherocompose.data.remote

import com.google.gson.annotations.SerializedName

class ResponseDto(
    val response: String,
    @SerializedName("results")
    val heroes: List<HeroDto>?,
    val error: String?
)

