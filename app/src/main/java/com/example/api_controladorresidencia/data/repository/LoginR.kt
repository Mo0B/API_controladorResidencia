package com.example.api_controladorresidencia.data.repository


import android.util.Log
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.LoginRequest


class LoginR(private val apiService: ApiService) {
    suspend fun login(usuario: String, contrasena: String): Result<String> {
        return try {
            val response = apiService.login(LoginRequest(usuario, contrasena))
            Log.d("LoginRepo", "Código respuesta: ${response.code()}")
            Log.d("LoginRepo", "Cuerpo respuesta: ${response.body()}")

            if (response.isSuccessful) {
                val token = response.body()?.token
                if (token != null) {
                    Result.success(token)
                } else {
                    Log.e("LoginRepo", "Token nulo")
                    Result.failure(Exception("Token no recibido"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("LoginRepo", "Error body: $errorBody")
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.e("LoginRepo", "Excepción: ${e.message}")
            Result.failure(e)
        }
    }


}
