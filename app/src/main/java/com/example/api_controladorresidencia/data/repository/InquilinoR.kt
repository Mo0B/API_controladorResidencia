package com.example.api_controladorresidencia.data.repository
import android.util.Log
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient
class InquilinoR(private val api: ApiService) {


    suspend fun getInquilinoS(): List<InquilinoM> {
        return api.getInquilinoS()
    }

    suspend fun getInquilino(id: Long): InquilinoM {
        return api.getInquilino(id)
    }

    suspend fun saveInquilino(inquilino: InquilinoM): InquilinoM? {
        return try {
            val response = api.saveInquilino(inquilino)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("REPO_INQ", "Error al guardar inquilino: ${response.errorBody()?.string()}")
                null
            }
        } catch (e: Exception) {
            Log.e("REPO_INQ", "Excepción al guardar inquilino: ${e.message}", e)
            null
        }
    }



    suspend fun deleteInquilino(id: Long) {
        api.deleteInquilino(id)
    }

    suspend fun updateInquilino(id: Long, inquilino: InquilinoM): InquilinoM {
        return api.updateInquilino(id, inquilino)
    }
}