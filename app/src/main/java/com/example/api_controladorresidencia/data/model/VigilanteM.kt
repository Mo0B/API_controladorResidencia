package com.example.api_controladorresidencia.data.model

data class VigilanteM(
    val id: Long = 0,
    val nombre: String = "",
    val apellido: String = "",
    val telefono: String = "",
    val num_Documento: String = "",
    val turno: String = "",
    val usuario: String = "",
    val contra: String = "",
    val accion: Boolean = true
)
