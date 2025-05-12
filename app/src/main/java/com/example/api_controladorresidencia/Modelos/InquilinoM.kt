package com.example.api_controladorresidencia.Modelos

data class InquilinoM(
    val id: Long = 0,
    val nombre: String,
    val apellido: String,
    val telefono: String,
    val num_Documento: String,
    val num_Torre: String,
    val num_Apartamento: String
)
