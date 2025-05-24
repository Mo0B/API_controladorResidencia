package com.example.api_controladorresidencia.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.repository.LoginR
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginR,
    private val controlSesion: ControlSesion
) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> = _loginError

    fun login(usuario: String, contra: String) {
        viewModelScope.launch {
            val result = repository.login(usuario, contra)
            result.onSuccess { token ->
                controlSesion.saveToken(token)
                _loginSuccess.value = true
                _loginError.value = null
            }.onFailure {
                _loginError.value = "Usuario o contraseña incorrectos"
                _loginSuccess.value = false
            }
        }
    }
}


