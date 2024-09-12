package pe.edu.upc.superherocompose.data.remote

import com.google.gson.annotations.SerializedName

data class Biography(
    val aliases: List<String>,
    val alignment: String,
    @SerializedName("full-name")
    val fullName: String,
    val publisher: String
)