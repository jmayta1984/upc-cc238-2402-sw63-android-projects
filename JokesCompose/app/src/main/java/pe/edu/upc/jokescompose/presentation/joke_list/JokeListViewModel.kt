package pe.edu.upc.jokescompose.presentation.joke_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.common.UIState
import pe.edu.upc.jokescompose.data.repository.JokeRepository
import pe.edu.upc.jokescompose.domain.Joke

class JokeListViewModel(private val repository: JokeRepository): ViewModel() {

    private val _state = mutableStateOf(UIState<List<Joke>>())
    val state: State<UIState<List<Joke>>> get() = _state


    fun getJokes() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
           val result = repository.getJokes()
            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = result.message?:"An error occurred.")
            }
        }
    }
}