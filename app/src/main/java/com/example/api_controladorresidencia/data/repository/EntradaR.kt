package com.example.api_controladorresidencia.data.repository



import android.util.Log
import com.example.api_controladorresidencia.data.Request.EntradaRequest
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient

class EntradaR(private val api: ApiService) {


    suspend fun getEntradaS(): List<EntradaM> {
        return api.getEntradaS()
    }

    suspend fun getEntrada(id: Long): EntradaM {
        return api.getEntrada(id)
    }



    suspend fun deleteEntrada(id: Long) {
        api.deleteEntrada(id)
    }

    suspend fun updateEntrada(id: Long, entrada: EntradaM): EntradaM {
        return api.updateEntrada(id, entrada)
    }


    suspend fun registrarEntrada(entradaRequest: EntradaRequest): EntradaM? {
        val response = api .registrarEntrada(entradaRequest)
        return if (response.isSuccessful) {
            response.body()
        } else {

            val errorBody = response.errorBody()?.string()

            Log.e("API_ERROR", "Código: ${response.code()}, Error: ${errorBody ?: "Error desconocido"}")
            null
        }
    }


}
