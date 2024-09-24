package pe.edu.upc.appturismo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.appturismo.common.Constants
import pe.edu.upc.appturismo.data.remote.PackageService
import pe.edu.upc.appturismo.data.repository.PackageRepository
import pe.edu.upc.appturismo.domain.TourPackage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PackageListScreen(viewModel: PackageListViewModel) {

    val state = viewModel.state.value
    val placeId = viewModel.placeId.value
    val typeId = viewModel.typeId.value

    val places = listOf(
        Place("S001", "Machu Picchu"),
        Place("S002", "Ayacucho"),
        Place("S003", "Chichen Itza"),
        Place("S004", " Cristo Redentor"),
        Place("S005", "Islas Malvinas"),
        Place("S006", "Muralla China"),
    )

    val types = listOf(
        Type("1", "Viajes"),
        Type("2", "Hospedajes")
    )

    Scaffold { paddingValues: PaddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyRow {
                items(places) { place ->
                    FilterChip(
                        modifier = Modifier.padding(4.dp),
                        selected = placeId == place.id,
                        label = {
                            Text(place.description)
                        },
                        leadingIcon = {
                            if (placeId == place.id) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        },
                        onClick = {
                            viewModel.onPlaceIdChanged(place.id)
                        }
                    )
                }
            }
            LazyRow {
                items(types) { type ->
                    FilterChip(
                        modifier = Modifier.padding(4.dp),
                        selected = typeId == type.id,
                        label = {
                            Text(type.description)
                        },
                        leadingIcon = {
                            if (typeId == type.id) {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        },
                        onClick = {
                            viewModel.onTypeIdChanged(type.id)
                        }
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()) {
                Text(state.message)
            }
            state.data?.let { packages: List<TourPackage> ->
                LazyColumn {
                    items(packages) {
                        Card(modifier = Modifier.padding(4.dp)) {
                            Column {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    GlideImage(
                                        modifier = Modifier
                                            .height(144.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        imageModel = { it.image }, // loading a network image using an URL.
                                        imageOptions = ImageOptions(
                                            contentScale = ContentScale.Crop,
                                            alignment = Alignment.Center
                                        )
                                    )
                                    IconButton(
                                        onClick = {}, modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(2.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.FavoriteBorder,
                                            contentDescription = "Favorite",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }


                                }

                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = it.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Text(modifier = Modifier.padding(4.dp), text = it.description)
                            }

                        }
                    }
                }
            }
        }
    }
}

data class Place(val id: String, val description: String)
data class Type(val id: String, val description: String)

@Preview
@Composable
fun PackageListScreenPreview() {
    val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val service = retrofit.create(PackageService::class.java)
    val repository = PackageRepository(service)
    val viewModel = PackageListViewModel(repository)
    PackageListScreen(viewModel)
}