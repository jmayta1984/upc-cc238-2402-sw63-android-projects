package pe.edu.upc.jokescompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import pe.edu.upc.jokescompose.common.Constants
import pe.edu.upc.jokescompose.data.local.AppDatabase
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.presentation.JokeScreen
import pe.edu.upc.jokescompose.presentation.JokeViewModel
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
            .databaseBuilder(applicationContext,AppDatabase::class.java, "jokes-db")
            .build()
            .getJokeDao()

        val viewModel = JokeViewModel(JokeRepository(service, dao))
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesComposeTheme {
                JokeScreen(viewModel)

            }
        }
    }
}

