package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class InquilinoR {
    private val api = RetrofitClient.instancia

    suspend fun getInquilinoS(): List<InquilinoM> {
        return api.getInquilinoS()
    }

    suspend fun getInquilino(id: Long): InquilinoM {
        return api.getInquilino(id)
    }

    suspend fun saveInquilino(inquilino: InquilinoM): InquilinoM {
        return api.saveInquilino(inquilino)
    }

    suspend fun deleteInquilino(id: Long) {
        api.deleteInquilino(id)
    }

    suspend fun updateInquilino(id: Long, inquilino: InquilinoM): InquilinoM {
        return api.updateInquilino(id, inquilino)
    }
}