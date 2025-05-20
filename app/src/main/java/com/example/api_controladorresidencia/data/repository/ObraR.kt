package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.ObraM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class ObraR {
    private val api = RetrofitClient.instancia

    suspend fun getObraS(): List<ObraM> {
        return api.getObraS()
    }

    suspend fun getObra(id: Long): ObraM {
        return api.getObra(id)
    }

    suspend fun saveObra(obra: ObraM): ObraM {
        return api.saveObra(obra)
    }

    suspend fun deleteObra(id: Long) {
        api.deleteObra(id)
    }

    suspend fun updateObra(id: Long, obra: ObraM): ObraM {
        return api.updateObra(id, obra)
    }
}