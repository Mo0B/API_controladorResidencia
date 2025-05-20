package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.CorrespondenciaM
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class CorrespondenciaR {
    private val api = RetrofitClient.instancia

    suspend fun getCorrespondenciaS(): List<CorrespondenciaM> {
        return api.getCorrespondenciaS()
    }

    suspend fun getCorrespondecia(id: Long): CorrespondenciaM {
        return api.getCorrespondecia(id)
    }

    suspend fun saveCorrespondencia(correspondencia: CorrespondenciaM): CorrespondenciaM {
        return api.saveCorrespondencia(correspondencia)
    }

    suspend fun deleteCorrespondencia(id: Long) {
        api.deleteCorrespondencia(id)
    }

    suspend fun updateCorrespondencia(id: Long, correspondencia: CorrespondenciaM): CorrespondenciaM {
        return api.updateCorrespondencia(id, correspondencia)
    }
}