package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.EntradaVehiculoM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class EntradaVehiculoR {
    private val api = RetrofitClient.instancia

    suspend fun getEntradaVehiculoS(): List<EntradaVehiculoM> {
        return api.getEntradaVehiculoS()
    }

    suspend fun getEntradaVehiculo(id: Long): EntradaVehiculoM {
        return api.getEntradaVehiculo(id)
    }

    suspend fun saveEntradaVehiculo(entradaVehiculo: EntradaVehiculoM): EntradaVehiculoM {
        return api.saveEntradaVehiculo(entradaVehiculo)
    }

    suspend fun deleteEntradaVehiculo(id: Long) {
        api.deleteEntradaVehiculo(id)
    }

    suspend fun updateEntradaVehiculo(id: Long, entradaVehiculo: EntradaVehiculoM): EntradaVehiculoM {
        return api.updateEntradaVehiculo(id, entradaVehiculo)
    }
}