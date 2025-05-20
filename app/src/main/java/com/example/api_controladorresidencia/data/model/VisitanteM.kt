package com.example.api_controladorresidencia.data.model


data class VisitanteM(
    val id: Long = 0,
    val nombre: String = "",
    val apellido: String = "",
    val telefono: String = "",
    val num_Documento: String = "",
    val razon: String = "",
    val fecha_visita: String = "",
    val inquilino: InquilinoM? = null,
    val vigilante_reg: VigilanteM? = null
)

