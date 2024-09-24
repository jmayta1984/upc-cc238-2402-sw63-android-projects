package pe.edu.upc.appturismo.common

data class UIState<T>(
    val isLoading: Boolean = false,
    var data: T? = null,
    val message: String = ""
)