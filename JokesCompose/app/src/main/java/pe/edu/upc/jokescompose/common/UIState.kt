package pe.edu.upc.jokescompose.common

data class UIState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String = ""
)