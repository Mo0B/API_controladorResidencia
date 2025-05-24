package com.example.api_controladorresidencia.data.repository

import androidx.room.Dao
import androidx.room.Query
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.LoginRequest
import com.example.api_controladorresidencia.data.network.RetrofitClient

class LoginR(private val apiService: ApiService) {
    suspend fun login(usuario: String, contrasena: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(usuario, contrasena))
            if (response.isSuccessful) {
                val token = response.body()?.token
                if (token != null) {
                    Result.success(token)
                } else {
                    Result.failure(Exception("Token no recibido"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
