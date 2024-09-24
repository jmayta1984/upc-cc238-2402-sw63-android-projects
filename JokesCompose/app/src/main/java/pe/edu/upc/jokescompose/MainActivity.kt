package pe.edu.upc.jokescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.room.Room
import pe.edu.upc.jokescompose.common.Constants
import pe.edu.upc.jokescompose.data.local.AppDatabase
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.presentation.joke.JokeScreen
import pe.edu.upc.jokescompose.presentation.joke.JokeViewModel
import pe.edu.upc.jokescompose.presentation.joke_list.JokeListScreen
import pe.edu.upc.jokescompose.presentation.joke_list.JokeListViewModel
import pe.edu.upc.jokescompose.ui.theme.JokesComposeTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val service = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeService::class.java)

        val dao = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "jokes-db")
            .build()
            .getJokeDao()

        val repository = JokeRepository(service, dao)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesComposeTheme {
                val items = listOf(
                    ItemTab(
                        "Search",
                        Icons.Filled.Search
                    ) ,
                    ItemTab(
                        "Favorites",
                        Icons.Filled.Favorite
                    )
                )


                val index = remember {
                    mutableStateOf(0)
                }

                Scaffold { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {

                        LazyRow {
                            itemsIndexed(items) { position, item ->

                                FilterChip(
                                    selected = index.value == position,
                                    leadingIcon = {
                                        Icon(item.icon, item.name)
                                    },
                                    label = {
                                        Text(item.name)
                                    },
                                    onClick = {
                                        index.value = position
                                    }
                                )
                            }
                        }
                        if (index.value == 0) {
                            val jokeViewModel = JokeViewModel(repository)
                            JokeScreen(jokeViewModel)
                        } else {
                            val jokeListViewModel = JokeListViewModel(repository)
                            JokeListScreen(jokeListViewModel)
                        }


                    }
                }

            }
        }
    }
}

data class ItemTab(
    val name: String,
    val icon: ImageVector
)

