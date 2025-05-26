package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient


class VigilanteR(
    private val apiService: ApiService,
    private val controlSesion: ControlSesion
) {

    suspend fun getVigilanteS(): List<VigilanteM> {
        return apiService.getVigilanteS()
    }

    suspend fun getLoggedVigilante(): Result<VigilanteM> {
        return try {
            val token = controlSesion.getToken() ?: return Result.failure(Exception("Token no disponible"))
            val response = apiService.getLoggedVigilante("Bearer $token")
            if (response.isSuccessful) {
                val vigilante = response.body()
                if (vigilante != null) {
                    Result.success(vigilante)
                } else {
                    Result.failure(Exception("Cuerpo de respuesta vacío"))
                }
            } else {
                Result.failure(Exception("Error en la respuesta: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getVigilante(id: Long): VigilanteM {
        return apiService.getVigilante(id)
    }

    suspend fun saveVigilante(vigilante: VigilanteM): VigilanteM {
        return apiService.saveVigilante(vigilante)
    }

    suspend fun deleteVigilante(id: Long) {
        apiService.deleteVigilante(id)
    }

    suspend fun updateVigilante(id: Long, vigilante: VigilanteM): VigilanteM {
        return apiService.updateVigilante(id, vigilante)
    }
}

