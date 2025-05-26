package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.MantenimientoM
import com.example.api_controladorresidencia.data.repository.EntradaVehiculoR
import com.example.api_controladorresidencia.data.repository.MantenimientoR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `MantenimientoViewModel`(
    private val mantenimientoR: MantenimientoR

) : ViewModel() {


    private val _mantenimiento = MutableStateFlow<List<MantenimientoM>>(emptyList())
    val mantenimiento: StateFlow<List<MantenimientoM>> = _mantenimiento

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mantenimientoSeleccionado = MutableStateFlow<MantenimientoM?>(null)
    val mantenimientoSeleccionado: StateFlow<MantenimientoM?> = _mantenimientoSeleccionado


    init {
        getMantenimientoS()
    }
    fun getMantenimientoS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = mantenimientoR.getMantenimientoS()
                _mantenimiento.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando mantenimientos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMantenimiento(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _mantenimientoSeleccionado.value = mantenimientoR.getMantenimiento(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando mantenimiento: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateMantenimiento(id: Long, nuevoMantenimiento: MantenimientoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                mantenimientoR.updateMantenimiento(id, nuevoMantenimiento)
                getMantenimientoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar mantenimiento: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMantenimiento(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                mantenimientoR.deleteMantenimiento(id)
                getMantenimientoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando mantenimiento: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveMantenimiento(mantenimiento: MantenimientoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                mantenimientoR.saveMantenimiento(mantenimiento)
                getMantenimientoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando mantenimiento: ${e.message}")
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