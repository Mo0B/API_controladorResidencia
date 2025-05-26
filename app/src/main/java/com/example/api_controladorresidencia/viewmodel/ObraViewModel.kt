package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.model.ObraM
import com.example.api_controladorresidencia.data.repository.ObraR
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class `ObraViewModel`(private val obraR: ObraR) : ViewModel() {



    private val _obra = MutableStateFlow<List<ObraM>>(emptyList())
    val obra: StateFlow<List<ObraM>> = _obra

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _obraSeleccionada = MutableStateFlow<ObraM?>(null)
    val obraSeleccionada: StateFlow<ObraM?> = _obraSeleccionada


    init {
        getObraS()
    }
    fun getObraS() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = obraR.getObraS()
                _obra.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando obras: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getObra(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _obraSeleccionada.value = obraR.getObra(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando obra: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun updateObra(id: Long, nuevaObra: ObraM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                obraR.updateObra(id, nuevaObra)
                getObraS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar obra: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteObra(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                obraR.deleteObra(id)
                getObraS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando obra: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveObra(obra: ObraM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                obraR.saveObra(obra)
                getObraS()
                onSuccess()
            } catch (e: Exception) {
                onError("Error guardando obra: ${e.message}")
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