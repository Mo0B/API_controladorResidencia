package com.example.api_controladorresidencia.data.repository


import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.LoginRequest


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
                val errorBody = response.errorBody()?.string()

                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {

            Result.failure(e)
        }
    }


}
