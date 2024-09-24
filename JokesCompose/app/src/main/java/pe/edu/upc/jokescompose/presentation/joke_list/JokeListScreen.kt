package pe.edu.upc.jokescompose.presentation.joke_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pe.edu.upc.jokescompose.domain.Joke

@Composable
fun JokeListScreen(viewModel: JokeListViewModel) {

    val state = viewModel.state.value
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()) {
                Text(state.message)
            }
            state.data?.let { jokes: List<Joke> ->
                LazyColumn {
                    items(jokes) { joke: Joke ->
                        Card(modifier = Modifier.padding(4.dp)) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(joke.description)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    for (i in 1..5) {
                                        IconButton(onClick = {
                                            viewModel.onScoreChanged(i, joke)
                                        }) {
                                            Icon(
                                                Icons.Filled.Star, "score",
                                                tint = if (i <= joke.score) Color.Red else Color.Gray
                                            )
                                        }
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }
    }
}