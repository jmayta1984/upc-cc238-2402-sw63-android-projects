package pe.edu.upc.superherocompose.data.remote

import com.google.gson.annotations.SerializedName

data class Connections(
    @SerializedName("group-affiliation" )
    val groupAffiliation: String,
    val relatives: String
)