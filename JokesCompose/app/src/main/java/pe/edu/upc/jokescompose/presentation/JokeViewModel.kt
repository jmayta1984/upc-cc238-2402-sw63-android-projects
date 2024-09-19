package pe.edu.upc.jokescompose.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.common.UIState
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _state = mutableStateOf(UIState<Joke>())
    val state: State<UIState<Joke>> get() = _state

    fun onScoreChanged(score: Int) {

        val newJoke: Joke
        _state.value.data?.let { joke ->
            newJoke = Joke(joke.description, if (score == joke.score) 0 else score)
            _state.value = UIState(data = newJoke)

            viewModelScope.launch {
                if (joke.score == 0) {
                    repository.insertJoke(newJoke)
                    return@launch
                }
                if (newJoke.score == 0) {
                    repository.deleteJoke(joke)
                    return@launch
                }
                if (newJoke.score > 0 && joke.score > 0) {
                    repository.updateJoke(newJoke)
                    return@launch
                }
            }

        }

    }

    fun getJoke() {
        _state.value = UIState(isLoading = true)

        viewModelScope.launch {
            val result = repository.getJoke()
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = result.message ?: "An error occurred")
            }

        }
    }
}