package pe.edu.upc.superherocompose.common

data class UIState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String = ""
)