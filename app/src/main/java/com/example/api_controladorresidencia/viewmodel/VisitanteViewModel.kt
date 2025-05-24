package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.VisitanteM
import com.example.api_controladorresidencia.data.repository.VisitanteR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `VisitanteViewModel` : ViewModel() {

    private val repository = VisitanteR()

    private val _visitante = MutableStateFlow<List<VisitanteM>>(emptyList())
    val visitante: StateFlow<List<VisitanteM>> = _visitante

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _visitanteSeleccionado = MutableStateFlow<VisitanteM?>(null)
    val visitanteSeleccionado: StateFlow<VisitanteM?> = _visitanteSeleccionado


    init {
        getVisitanteS()
    }
    fun getVisitanteS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getVisitanteS()
                _visitante.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando visitantes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getVisitante(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _visitanteSeleccionado.value = repository.getVisitante(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando visitante: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateVisitante(id: Long, nuevoVisitante: VisitanteM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateVisitante(id, nuevoVisitante)
                getVisitanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar visitante: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteVisitante(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteVisitante(id)
                getVisitanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando visitante: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveVisitante(visitante: VisitanteM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveVisitante(visitante)
                getVisitanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando visitante: ${e.message}")
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