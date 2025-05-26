package com.example.api_controladorresidencia.data.repository
import android.util.Log
import com.example.api_controladorresidencia.data.Request.EntradaRequest
import com.example.api_controladorresidencia.data.Request.VisitanteRequest
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VisitanteM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient
class VisitanteR(private val api: ApiService) {


    suspend fun getVisitanteS(): List<VisitanteM> {
        return api.getVisitanteS()
    }

    suspend fun getVisitante(id: Long): VisitanteM {
        return api.getVisitante(id)
    }

    suspend fun saveVisitante(visitante: VisitanteM): VisitanteM {
        return api.saveVisitante(visitante)
    }

    suspend fun deleteVisitante(id: Long) {
        api.deleteVisitante(id)
    }

    suspend fun updateVisitante(id: Long, visitante: VisitanteM): VisitanteM {
        return api.updateVisitante(id, visitante)
    }

    suspend fun registrarVisitante(visitanteRequest: VisitanteRequest): VisitanteM? {
        val response = api .registrarVisita(visitanteRequest)
        return if (response.isSuccessful) {
            response.body()
        } else {

            val errorBody = response.errorBody()?.string()

            Log.e("API_ERROR", "Código: ${response.code()}, Error: ${errorBody ?: "Error desconocido"}")
            null
        }
    }

}