package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.repository.VigilanteR

class VigilanteViewModelFactory(
    private val vigilanteRepository: VigilanteR,
    private val controlSesion: ControlSesion
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VigilanteViewModel::class.java)) {
            return VigilanteViewModel(vigilanteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
