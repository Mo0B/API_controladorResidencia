package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VehiculoM
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.VehiculoR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `VehiculoViewModel` : ViewModel() {

    private val repository = VehiculoR()

    private val _vehiculo = MutableStateFlow<List<VehiculoM>>(emptyList())
    val vehiculo: StateFlow<List<VehiculoM>> = _vehiculo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _vehiculoSeleccionado = MutableStateFlow<VehiculoM?>(null)
    val vehiculoSeleccionado: StateFlow<VehiculoM?> = _vehiculoSeleccionado


    init {
        getVehiculoS()
    }
    fun getVehiculoS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getVehiculoS()
                _vehiculo.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando vehículos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getVehiculo(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _vehiculoSeleccionado.value = repository.getVehiculo(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando vehículo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateVehiculo(id: Long, nuevoVehiculo: VehiculoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateVehiculo(id, nuevoVehiculo)
                getVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar vehículo: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteVehiculo(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteVehiculo(id)
                getVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando vehículo: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveVehiculo(vehiculo: VehiculoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveVehiculo(vehiculo)
                getVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando vehículo: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun setError(msg: String) {
        _error.value = msg
    }
}