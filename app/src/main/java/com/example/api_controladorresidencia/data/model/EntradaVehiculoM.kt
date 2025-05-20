package com.example.api_controladorresidencia.data.model

data class EntradaVehiculoM(
    val id: Long = 0,
    val fecha_entrada: String,
    val fecha_salida: String,
    val vigilante_reg: VigilanteM,
    val vehiculo: VehiculoM
)
