package pe.edu.upc.jokescompose.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun JokeScreen(viewModel: JokeViewModel){
    Scaffold { paddingValues ->
        Column (modifier = Modifier.padding(paddingValues))
        {
            OutlinedButton(onClick = {

            }) { }
        }
    }
}