package pe.edu.upc.appturismo.data.remote

import com.google.gson.annotations.SerializedName
import pe.edu.upc.appturismo.domain.TourPackage

data class PackageDto(
    val des1: String,
    val des2: String,
    val des3: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("estrellas")
    val stars: String,
    @SerializedName("idProducto")
    val id: String,
    @SerializedName("idSitio")
    val placeId: String,
    @SerializedName("idTipo")
    val typeId: String,
    @SerializedName("imagen")
    val image: String,
    @SerializedName("nombre")
    val name: String,
    val pre1: String,
    val pre2: String,
    val pre3: String,
    @SerializedName("ubicacin")
    val location: String
)

fun PackageDto.toPackage(): TourPackage = TourPackage(id, name, description, image, location)
