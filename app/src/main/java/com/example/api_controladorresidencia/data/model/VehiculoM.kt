package com.example.api_controladorresidencia.data.model

data class VehiculoM(
    val id: Long = 0,
    val num_Placa: String,
    val tipo: String,
    val inquilino: InquilinoM? = null,
    val visitante: VisitanteM? = null
)
