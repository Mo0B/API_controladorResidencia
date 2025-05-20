package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.VehiculoM
import com.example.api_controladorresidencia.data.network.RetrofitClient
class VehiculoR {
    private val api = RetrofitClient.instancia

    suspend fun getVehiculoS(): List<VehiculoM> {
        return api.getVehiculoS()
    }

    suspend fun getVehiculo(id: Long): VehiculoM {
        return api.getVehiculo(id)
    }

    suspend fun saveVehiculo(vehiculo: VehiculoM): VehiculoM {
        return api.saveVehiculo(vehiculo)
    }

    suspend fun deleteVehiculo(id: Long) {
        api.deleteVehiculo(id)
    }

    suspend fun updateVehiculo(id: Long, vehiculo: VehiculoM): VehiculoM {
        return api.updateVehiculo(id, vehiculo)
    }
}