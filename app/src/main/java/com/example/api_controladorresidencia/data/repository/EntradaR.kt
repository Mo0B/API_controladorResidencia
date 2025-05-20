package com.example.api_controladorresidencia.data.repository



import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.network.RetrofitClient

class EntradaR {
    private val api = RetrofitClient.instancia

    suspend fun getEntradaS(): List<EntradaM> {
        return api.getEntradaS()
    }

    suspend fun getEntrada(id: Long): EntradaM {
        return api.getEntrada(id)
    }

    suspend fun saveEntrada(entrada: EntradaM): EntradaM {
        return api.saveEntrada(entrada)
    }

    suspend fun deleteEntrada(id: Long) {
        api.deleteEntrada(id)
    }

    suspend fun updateEntrada(id: Long, entrada: EntradaM): EntradaM {
        return api.updateEntrada(id, entrada)
    }

}
