package com.example.api_controladorresidencia.data.model

data class ObraM(
    val id: Long = 0,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val num_Documento: String,
    val tipo: String,
    val inquilino: InquilinoM
)
