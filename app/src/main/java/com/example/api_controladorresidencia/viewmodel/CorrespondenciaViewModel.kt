package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.CorrespondenciaM
import com.example.api_controladorresidencia.data.repository.CorrespondenciaR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `CorrespondenciaViewModel` : ViewModel() {
    private val repository = CorrespondenciaR()

    private val _correspondencia = MutableStateFlow<List<CorrespondenciaM>>(emptyList())
    val correspondencia: StateFlow<List<CorrespondenciaM>> = _correspondencia

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _correspondenciaSeleccionada = MutableStateFlow<CorrespondenciaM?>(null)
    val correspondenciaSeleccionada: StateFlow<CorrespondenciaM?> = _correspondenciaSeleccionada

    init {
        getCorrespondenciaS()
    }
    fun getCorrespondenciaS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getCorrespondenciaS()
                _correspondencia.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando correspondencias: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCorrespondencia(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _correspondenciaSeleccionada.value = repository.getCorrespondencia(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando correspondencia: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateCorrespondencia(id: Long, nuevaCorrespondencia: CorrespondenciaM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateCorrespondencia(id, nuevaCorrespondencia)
                getCorrespondenciaS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar correspondencia: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCorrespondencia(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteCorrespondencia(id)
                getCorrespondenciaS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando correspondencia: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveCorrespondencia(correspondencia: CorrespondenciaM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveCorrespondencia(correspondencia)
                getCorrespondenciaS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando correspondencia: ${e.message}")
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