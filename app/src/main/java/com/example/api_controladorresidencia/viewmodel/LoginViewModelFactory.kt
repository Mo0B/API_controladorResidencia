package com.example.api_controladorresidencia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.repository.LoginR
import com.example.api_controladorresidencia.data.repository.VigilanteR

class LoginViewModelFactory(
    private val repository: LoginR,
    private val controlSesion: ControlSesion,
    private val repositoryVigilanteR: VigilanteR
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository, controlSesion,repositoryVigilanteR) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

