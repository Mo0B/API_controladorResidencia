package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.EntradaVehiculoM
import com.example.api_controladorresidencia.data.repository.EntradaVehiculoR
import com.example.api_controladorresidencia.data.repository.InquilinoR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `EntradaVehiculoViewModel`(
    private val entradaVehiculoR: EntradaVehiculoR

) : ViewModel() {


    private val _entradaVehiculo = MutableStateFlow<List<EntradaVehiculoM>>(emptyList())
    val entradaVehiculo: StateFlow<List<EntradaVehiculoM>> = _entradaVehiculo

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _entradaVehiculoSeleccionada = MutableStateFlow<EntradaVehiculoM?>(null)
    val entradaVehiculoSeleccionada: StateFlow<EntradaVehiculoM?> = _entradaVehiculoSeleccionada


    init {
        getEntradaVehiculoS()
    }

    fun getEntradaVehiculoS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = entradaVehiculoR.getEntradaVehiculoS()
                _entradaVehiculo.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entrada de vehículos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEntradaVehiculo(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _entradaVehiculoSeleccionada.value = entradaVehiculoR.getEntradaVehiculo(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entrada de vehículo: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateEntradaVehiculo(id: Long, nuevaEntradaVehiculo: EntradaVehiculoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                entradaVehiculoR.updateEntradaVehiculo(id, nuevaEntradaVehiculo)
                getEntradaVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar entradaVehiculo: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteEntradaVehiculo(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                entradaVehiculoR.deleteEntradaVehiculo(id)
                getEntradaVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando entradaVehiculo: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveEntradaVehiculo(entradaVehiculo: EntradaVehiculoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                entradaVehiculoR.saveEntradaVehiculo(entradaVehiculo)
                getEntradaVehiculoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando entradaVehiculo: ${e.message}")
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