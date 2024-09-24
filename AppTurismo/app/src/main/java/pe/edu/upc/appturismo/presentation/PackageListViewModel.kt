package pe.edu.upc.appturismo.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.appturismo.common.Resource
import pe.edu.upc.appturismo.common.UIState
import pe.edu.upc.appturismo.data.repository.PackageRepository
import pe.edu.upc.appturismo.domain.TourPackage

class PackageListViewModel(private val repository: PackageRepository) : ViewModel() {
    private val _state = mutableStateOf(UIState<List<TourPackage>>())
    val state: State<UIState<List<TourPackage>>> get() =  _state

    private val _placeId = mutableStateOf("")
    val placeId: State<String> get() =  _placeId

    private val _typeId = mutableStateOf("")
    val typeId: State<String> get() =  _typeId

    fun onPlaceIdChanged(id: String){
        _placeId.value = id
        getPackages()
    }

    fun onTypeIdChanged(id: String){
        _typeId.value = id
        getPackages()
    }

    private fun getPackages() {
        _state.value = UIState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getPackages(_placeId.value, _typeId.value)

            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = result.message?: "An error occurred.")
            }
        }
    }
}