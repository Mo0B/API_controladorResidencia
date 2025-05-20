package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.VigilanteR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `VigilanteViewModel` : ViewModel() {

    private val repository = VigilanteR()

    private val _vigilante = MutableStateFlow<List<VigilanteM>>(emptyList())
    val vigilante: StateFlow<List<VigilanteM>> = _vigilante

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _vigilanteSeleccionado = MutableStateFlow<VigilanteM?>(null)
    val vigilanteSeleccionado: StateFlow<VigilanteM?> = _vigilanteSeleccionado


    init {
        getVigilanteS()
    }
    fun getVigilanteS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getVigilanteS()
                _vigilante.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando vigilantes: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getVigilante(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _vigilanteSeleccionado.value = repository.getVigilante(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando vigilante: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateVigilante(id: Long, nuevoVigilante: VigilanteM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateVigilante(id, nuevoVigilante)
                getVigilanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar vigilante: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteVigilante(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteVigilante(id)
                getVigilanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando vigilante: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveVigilante(vigilante: VigilanteM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveVigilante(vigilante)
                getVigilanteS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando vigilante: ${e.message}")
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