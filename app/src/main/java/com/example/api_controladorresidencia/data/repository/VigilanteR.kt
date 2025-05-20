package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class VigilanteR {
    private val api = RetrofitClient.instancia

    suspend fun getVigilanteS(): List<VigilanteM> {
        return api.getVigilanteS()
    }

    suspend fun getVigilante(id: Long): VigilanteM {
        return api.getVigilante(id)
    }

    suspend fun saveVigilante(vigilante: VigilanteM): VigilanteM {
        return api.saveVigilante(vigilante)
    }

    suspend fun deleteVigilante(id: Long) {
        api.deleteVigilante(id)
    }

    suspend fun updateVigilante(id: Long, vigilante: VigilanteM): VigilanteM {
        return api.updateVigilante(id, vigilante)
    }
}