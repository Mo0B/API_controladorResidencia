package com.example.api_controladorresidencia.data.repository
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.MantenimientoM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient
class MantenimientoR(private val api: ApiService) {


    suspend fun getMantenimientoS(): List<MantenimientoM> {
        return api.getMantenimientoS()
    }

    suspend fun getMantenimiento(id: Long): MantenimientoM {
        return api.getMantenimiento(id)
    }

    suspend fun saveMantenimiento(mantenimiento: MantenimientoM): MantenimientoM {
        return api.saveMantenimiento(mantenimiento)
    }

    suspend fun deleteMantenimiento(id: Long) {
        api.deleteMantenimiento(id)
    }

    suspend fun updateMantenimiento(id: Long, mantenimiento: MantenimientoM): MantenimientoM {
        return api.updateMantenimiento(id, mantenimiento)
    }
}