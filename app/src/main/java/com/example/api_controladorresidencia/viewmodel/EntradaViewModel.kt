package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.repository.EntradaR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `EntradaViewModel` : ViewModel() {

    private val repository = EntradaR()

    private val _entradas = MutableStateFlow<List<EntradaM>>(emptyList())
    val entradas: StateFlow<List<EntradaM>> = _entradas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _entradaSeleccionada = MutableStateFlow<EntradaM?>(null)
    val entradaSeleccionada: StateFlow<EntradaM?> = _entradaSeleccionada

    init {
        obtenerEntradas()
    }

    fun obtenerEntradas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getEntradaS()
                _entradas.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entradas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun obtenerEntrada(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _entradaSeleccionada.value = repository.getEntrada(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entrada: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun actualizarEntrada(id: Long, nuevaEntrada: EntradaM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateEntrada(id, nuevaEntrada)
                obtenerEntradas()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar entrada: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun eliminarEntrada(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteEntrada(id)
                obtenerEntradas()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando entrada: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crearEntrada(entrada: EntradaM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveEntrada(entrada)
                obtenerEntradas()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando entrada: ${e.message}")
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