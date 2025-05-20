package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.InquilinoR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `InquilinoViewModel` : ViewModel() {

    private val repository = InquilinoR()

    private val _inquilino = MutableStateFlow<List<InquilinoM>>(emptyList())
    val inquilino: StateFlow<List<InquilinoM>> = _inquilino

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _inquilinoSeleccionado = MutableStateFlow<InquilinoM?>(null)
    val inquilinoSeleccionado: StateFlow<InquilinoM?> = _inquilinoSeleccionado


    init {
        getInquilinoS()
    }
    fun getInquilinoS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.getInquilinoS()
                _inquilino.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando inquilinos: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getInquilino(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _inquilinoSeleccionado.value = repository.getInquilino(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando inquilino: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun updateInquilino(id: Long, nuevoInquilino: InquilinoM, viewModel: InquilinoViewModel, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.updateInquilino(id, nuevoInquilino)
                getInquilinoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar inquilino: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteInquilino(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.deleteInquilino(id)
                getInquilinoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando inquilino: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveInquilino(inquilino: InquilinoM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.saveInquilino(inquilino)
                getInquilinoS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando inquilino: ${e.message}")
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