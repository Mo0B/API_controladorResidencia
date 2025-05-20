package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VisitanteM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class VisitanteR {
    private val api = RetrofitClient.instancia

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
}