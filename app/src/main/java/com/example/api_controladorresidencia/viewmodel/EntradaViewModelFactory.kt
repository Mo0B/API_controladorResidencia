package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.repository.EntradaR

class EntradaViewModelFactory(
    private val entradaR: EntradaR,
    private val controlSesion: ControlSesion
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntradaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntradaViewModel(entradaR, controlSesion) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


