package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.InquilinoR

class InquilinoViewModelFactory(
    private val inquilinoR: InquilinoR

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InquilinoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InquilinoViewModel(inquilinoR) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}